package main

trait SpaceLike extends Plane2d {
  def apply(p: Point): Tile
  def update(p: Point, tile: Tile): Unit

  def getSubSpace(p: Point, dim: Dim): SubSpace
}
