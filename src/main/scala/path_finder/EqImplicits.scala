package path_finder

import cats._
import cats.implicits._
import common.Point

object EqImplicits {
  implicit val pointEq: Eq[Point] = Eq.instance[Point]((a:Point, b:Point) => a.x === b.x && a.y === b.y)
  implicit val cellEq: Eq[Cell] = Eq.instance[Cell](_ == _)
}
