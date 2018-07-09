package main

trait SpaceLike[T] extends Plane2d {
  def apply(p: Point): T
  def update(p: Point, tile: T): Unit

  def getSubSpace(p: Point, dim: Dim): SubSpace
}
