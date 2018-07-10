package common

import scala.language.implicitConversions
import math._

case class Point(x: Int, y: Int) {
  def + (that: Point): Point =
    Point(this.x + that.x, this.y + that.y)

  def - (that: Point): Point =
    Point(this.x - that.x, this.y - that.y)

  def * (n: Int): Point =
    Point(x * n, y * n)

  def dist(that: Point): Double = {
    sqrt(pow(that.x - this.x, 2) + pow(that.y - this.y, 2))
  }

  def toDim: Dim = Dim(x, y)
}

object Point {
  implicit def pointToDim(p: Point): Dim = Dim(p.x, p.y)
  implicit def dimToPoint(d: Dim): Point = Point(d.width, d.height)
}
