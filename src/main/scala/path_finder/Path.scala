package path_finder

import cats._
import cats.implicits._
import EqImplicits._

case class Path (space: Space, points: Seq[Point]) {
  def valid(): Boolean = {
    val isBlocked =
      (for (point <- points) yield {
        space.p(point) =!= CellEmpty
      }).fold(false)(_ || _)
    ! isBlocked
  }
}

import scala.collection.SortedSet
import scala.math.pow
import scala.collection.mutable

case class PathPoint(pos: Point, score: Double, parent: PathPoint)

object Path {
  def find(space: Space, start:Point, goal:Point): Path = {
    var queue: mutable.SortedSet[PathPoint] =
      mutable.SortedSet.empty(Ordering.by[PathPoint, Double](_.score))

    queue += PathPoint(start, 0, null)

    def score(cur:Point): Double =
      pow(cur.x - goal.x, 2) + pow(cur.y - goal.y, 2)

    def neighbors(pos:Point): Seq[Point] = {
      pos match {
        case Point(x, y) => {
          for {
            dx: Int <- -1 to 1
            dy: Int <- -1 to 1
            if !(dx == 0 && dy == 0)
            if space.isAccesable(x + dx, y + dy)
          } yield Point(x + dx, y + dy)
        }
      }
    }

    def iter(steps: Int): PathPoint = {
      if(queue.isEmpty) ???

      val cur = queue.head
      queue -= cur

      if(cur.pos === goal) cur
      else {
        for{
          neighbor <- neighbors(cur.pos)
        } {
          val nextPPoint = PathPoint(neighbor, steps + score(neighbor), cur)
          queue += nextPPoint
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
