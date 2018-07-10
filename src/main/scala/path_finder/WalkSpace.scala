package path_finder
import cats._
import cats.implicits._
import EqImplicits._
import common.{Dim, Point, SpaceLike}

trait WalkSpace extends SpaceLike[Cell] {
  def isAccesable(p: Point): Boolean =
    inBound(p) && this(p) === Cell.Empty
}

object WalkSpace {
  def fromOtherSpace[T](space: SpaceLike[T], predCanWalk: T => Boolean): WalkSpace = {
    new WalkSpace {
      val dim = space.dim

      def apply(p: Point): Cell = {
        if( predCanWalk(space(p)) ) Cell.Full
        else Cell.Empty
      }

      def update(p: Point, cell: Cell): Unit = ()
    }
  }

  def persist(initDim: Dim): WalkSpace = {
    new WalkSpace {
      val dim: Dim = initDim
      val space = GenSpacePersist[Cell](dim, Cell.Empty)
      def apply(p: Point): Cell = space(p)
      def update(p: Point, c: Cell): Unit = {
        space(p) = c
      }
    }
  }
}
