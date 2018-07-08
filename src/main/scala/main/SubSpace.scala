package main

class SubSpace(parent: SpaceLike,
               val parentPoint: Point,
               val dim: Dim
              ) extends SpaceLike {
  override def apply(p: Point): Tile =
    parent(parentPoint + p)

  override def update(p: Point, tile: Tile): Unit = {
    parent(parentPoint + p) = tile
  }

  def getSubSpace(p: Point, subDim: Dim): SubSpace = {
    new SubSpace(this, parentPoint + p, subDim)
  }
}
