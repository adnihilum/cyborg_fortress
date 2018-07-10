package main

class SubSpace[T](parent: SpaceLike[T],
               val parentPoint: Point,
               val dim: Dim
              ) extends SpaceLike[T] {
  override def apply(p: Point): T =
    parent(parentPoint + p)

  override def update(p: Point, tile: T): Unit = {
    parent(parentPoint + p) = tile
  }
}
