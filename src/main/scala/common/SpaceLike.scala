package common

trait SpaceLike[T] extends Plane2d {
  def apply(p: Point): T
  def update(p: Point, tile: T): Unit
  def getSubSpace(p: Point, subDim: Dim): SubSpace[T] =
    new SubSpace(this, p, subDim)
}
