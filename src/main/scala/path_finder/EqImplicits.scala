package path_finder

import cats._
import cats.implicits._

object EqImplicits {
  // Point
  implicit val pointEq = Eq.instance[Point]((a:Point, b:Point) => a.x === b.x && a.y === b.y)

  // Cell
  implicit val cellEq = Eq.instance[Cell](_ == _)
}
