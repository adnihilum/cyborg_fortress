package path_finder
import cats._
import cats.implicits._
import EqImplicits._
import common.{Dim, Point, SpaceLike}

trait WalkSpace extends SpaceLike[Cell] {
  def isAccesable(p: Point): Boolean =
    inBound(p) && this(p) === Cell.Empty

  def toTextForm: String = {
    (for {
      y <- 0 until dim.height
      x <- 0 until dim.width
      p = Point(x, y)
    } yield {
      this(p) match {
        case Cell.Full => '#'
        case Cell.Empty => '.'
      }
    }).toList.mkString("")
  }
}

object WalkSpace {
  def fromOtherSpace[T](space: SpaceLike[T], predCanWalk: (Point, T) => Boolean): WalkSpace = {
    new WalkSpace {
      val dim: Dim = space.dim

      def apply(p: Point): Cell = {
        if( predCanWalk(p, space(p)) ) Cell.Empty
        else Cell.Full
      }

      def update(p: Point, cell: Cell): Unit = throw new NotImplementedError()
    }
  }

  def persist(initDim: Dim): WalkSpace = {
    new WalkSpace {
      val dim: Dim = initDim
      val space: SpaceLike[Cell] = GenSpacePersist[Cell](dim, Cell.Empty)
      def apply(p: Point): Cell = space(p)
      def update(p: Point, c: Cell): Unit = {
        space(p) = c
      }
    }
  }

  def fromString(dim: Dim, str: String): WalkSpace = {
    val points =
      for{
        y <- 0 until dim.height
        x <- 0 until dim.width
      } yield Point(x, y)

    val chars: Set[Char] = ".#SE".toSet
    val cleanStr = str.filter (chars.contains)
    val space = persist(dim)

    for ((p, c) <- points.zip(cleanStr)) {
      space(p) = c match {
        case '.' | 'S' | 'E'  => Cell.Empty
        case '#' => Cell.Full
      }
    }
    space
  }

}
