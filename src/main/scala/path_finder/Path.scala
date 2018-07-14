package path_finder

import cats._
import cats.implicits._
import EqImplicits._
import ShowImplicits._
import common.Point

import scala.collection.SortedSet
import scala.math.pow
import scala.collection.mutable

case class Path (space: WalkSpace, points: Seq[Point]) {
  def valid(): Boolean = {
    val isBlocked =
      (for (point <- points) yield {
        space(point) =!= Cell.Empty
      }).fold(false)(_ || _)
    ! isBlocked
  }
}


object Path {
  case class PathPoint(pos: Point, score: Double, parent: PathPoint)
  implicit val orderingForPoint: Ordering[Point] = Ordering.by(p => (p.x, p.y))

  def findP(space: WalkSpace, start: Point, goal: Point): Option[Path] = {
    val (time, result) = common.Profiling.time(find(space, start, goal))
    if(time > 600) {
      println("slow path finding:")
      result match {
        case Some(path) => println(path.show)
        case None => println((space, start, goal).show)
      }
      println(s"start: $start")
      println(s"goal: $goal")
      println(s"time: $time")
    }
    result
  }

  def find(space: WalkSpace, start: Point, goal: Point): Option[Path] = {
    //println("find path")
    //println(s"space: ${space.show}")
    //println(s"start: $start")
    //println(s"stop: $goal")

    var queue: mutable.SortedSet[PathPoint] =
      mutable.SortedSet.empty(Ordering.by(x => (x.score, x.pos)))
    var knownPoints: mutable.Set[Point] = mutable.Set()

    queue += PathPoint(start, 0, null)
    knownPoints += start

    def score(cur: Point): Double =
      pow(cur.x - goal.x, 2) + pow(cur.y - goal.y, 2)

    def neighbors(p: Point): Seq[Point] = {
      for {
        dx: Int <- -1 to 1
        dy: Int <- -1 to 1
        if !(dx == 0 && dy == 0) && (dx == 0 || dy == 0)
        dp = Point(dx, dy)
        if space.isAccesable(p + dp)
      } yield p + dp
    }

    def iter(steps: Int): Option[PathPoint] = {
      if(queue.isEmpty) None
      else {
        val cur = queue.head

        queue -= cur

        if (cur.pos === goal) Some(cur)
        else {
          for {
            neighbor <- neighbors(cur.pos)
          } {
            val nextPPoint = PathPoint(neighbor, steps + score(neighbor), cur)
            if (!knownPoints.contains(nextPPoint.pos))
              queue += nextPPoint
              knownPoints += nextPPoint.pos
          }
          iter(steps + 1)
        }
      }
    }

    def traceBack(cur: PathPoint, acc: List[Point]): Seq[Point] = {
      val nextAcc = cur.pos :: acc
      if (cur.pos === start) nextAcc.toSeq
      else traceBack(cur.parent, nextAcc)
    }

    if (space.isAccesable(start) &&
        space.isAccesable(goal) &&
        start != goal
    ) {
        for (pathPoint <- iter(steps = 0)) yield
          new Path(space, traceBack(pathPoint, List()))
    } else None
  }
}
