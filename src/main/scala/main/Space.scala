package main

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
