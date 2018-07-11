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

  def find(space: WalkSpace, start:Point, goal:Point): Path = {
    var queue: mutable.SortedSet[PathPoint] =
      mutable.SortedSet.empty(Ordering.by(x => (x.score, x.pos)))
    var visitedPoints: mutable.Set[Point] = mutable.Set()

    queue += PathPoint(start, 0, null)

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

    def iter(steps: Int): PathPoint = {
      if(queue.isEmpty) throw new Exception("there is no path")
      //println(s"queue.length = ${queue.size}")
      val cur = queue.head
      //println(s"cur point: ${cur.pos}")

      queue -= cur
      visitedPoints += cur.pos

      if(cur.pos === goal) cur
      else {
        for{
          neighbor <- neighbors(cur.pos)
        } {
          val nextPPoint = PathPoint(neighbor, steps + score(neighbor), cur)
          //println(s"nextPPoint = $nextPPoint")
          if (!visitedPoints.contains(nextPPoint.pos))
            queue += nextPPoint
          //else
            //println(s"visited that point")
        }
        iter(steps + 1)
      }
    }

    def traceBack(cur: PathPoint, acc: List[Point]): Seq[Point] = {
      val nextAcc = cur.pos :: acc
      if (cur.pos === start) nextAcc.toSeq
      else traceBack(cur.parent, nextAcc)
    }


    val pathPoints = traceBack(iter(steps = 0), List())
    new Path(space, pathPoints)
  }
}
