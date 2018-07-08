package main

trait SpaceLike extends Plane2d {
  def apply(x: Int, y: Int): Tile
  def update(x: Int, y: Int, tile: Tile): Unit

  def getSubSpace(x: Int, y: Int, width: Int, height: Int): SubSpace
}

class SubSpace(parent: SpaceLike,
               val parentX: Int,
               val parentY: Int,
               val width: Int,
               val height: Int
              ) extends SpaceLike {
  override def apply(x: Int, y: Int): Tile =
    parent(parentX + x, parentY + y)

  override def update(x: Int, y: Int, tile: Tile): Unit = {
    parent(parentX + x, parentY + y) = tile
  }

  def getSubSpace(x: Int, y: Int, subWidth: Int, subHeight: Int): SubSpace = {
    new SubSpace(this, parentX + x, parentY + y, subWidth, subHeight)
  }
}

class Space(val width: Int,
            val height: Int
           ) extends SpaceLike {
  val tiles: Array[Tile] = Array.ofDim(width * height)
  fill(Tile.Empty)

  def fill(tile: Tile): Unit = {
    for ((x, y) <- iterate) {
      tiles(idx(x, y)) = tile
    }
  }

  // SpaceLike interface
  override def apply(x: Int, y: Int): Tile = {
    println(s"x = $x, y = $y")
    if (inBound(x, y))
      tiles(idx(x, y))
    else
      Tile.Nothing
  }

  override def update(x: Int, y: Int, tile: Tile): Unit = {
    tiles(idx(x, y)) = tile
  }

  override def getSubSpace(x: Int, y: Int, subWidth: Int, subHeight: Int): SubSpace =
    new SubSpace(this, x, y, subWidth, subHeight)
}