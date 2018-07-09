package path_finder

import cats._
import cats.implicits._
import main.Point

object EqImplicits {
  implicit val pointEq = Eq.instance[Point]((a:Point, b:Point) => a.x === b.x && a.y === b.y)
  implicit val cellEq = Eq.instance[Cell](_ == _)
}
