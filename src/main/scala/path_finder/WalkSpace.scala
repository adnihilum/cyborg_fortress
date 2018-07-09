package path_finder
import cats._
import cats.implicits._
import EqImplicits._
import main.{Dim, Point}

case class WalkSpace(override val dim: Dim)
  extends GenSpace[Cell](dim) {

  def isAccesable(p: Point): Boolean =
    inBound(p) && this(p) === CellEmpty

  for(p <- iterate) this(p) = CellEmpty
}

