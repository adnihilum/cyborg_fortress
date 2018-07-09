package main

trait Plane2d {
  val dim: Dim

  def iterate: Iterator[Point] = {
    for {
      x <- (0 until dim.width).toIterator
      y <- 0 until dim.height
    } yield Point(x, y)
  }

  def idx(p: Point): Int = {
    if(!inBound(p))
      throw new IndexOutOfBoundsException()
    p.x + p.y * dim.width
  }

  def inBound(p: Point): Boolean =
    p.x >= 0 && p.x < dim.width && p.y >= 0 && p.y < dim.height
}
