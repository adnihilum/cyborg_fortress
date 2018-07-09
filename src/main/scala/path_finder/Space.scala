package path_finder
import cats._
import cats.implicits._
import EqImplicits._

case class Space (override val width: Int, override val height: Int)
  extends GenSpace[Cell](width, height, CellEmpty) {

  def isAccesable(x:Int, y:Int): Boolean = inBound(x, y) && this(x,y) === CellEmpty
}

object Space {
  def apply(x:Int, y:Int): Space = new Space(x, y)
}
