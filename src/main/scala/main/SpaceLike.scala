package main

trait SpaceLike extends Plane2d {
  def apply(p: Point): Tile
  def update(p: Point, tile: Tile): Unit

  def getSubSpace(p: Point, dim: Dim): SubSpace
}

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

class Space(val dim: Dim) extends SpaceLike {
  val tiles: Array[Tile] = Array.ofDim(dim.width * dim.height)
  fill(Tile.Empty)

  def fill(tile: Tile): Unit = {
    for (p <- iterate) {
      tiles(idx(p)) = tile
    }
  }

  // SpaceLike interface
  override def apply(p: Point): Tile = {
    if (inBound(p))
      tiles(idx(p))
    else
      Tile.Nothing
  }

  override def update(p:Point, tile: Tile): Unit = {
    tiles(idx(p)) = tile
  }

  override def getSubSpace(p: Point, subDim: Dim): SubSpace =
    new SubSpace(this, p, subDim)
}