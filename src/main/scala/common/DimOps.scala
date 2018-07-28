package common

import scala.language.implicitConversions

object DimOps {
  implicit def dimToPoint(d: Dim): Point = Point(d.width, d.height)
  implicit def pointToDim(p: Point): Dim = Dim(p.x, p.y)
}
