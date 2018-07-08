package main

trait Plane2d {
  val width: Int
  val height: Int

  def iterate: Iterator[(Int, Int)] = for {
    x <- (0 until width).toIterator
    y <- 0 until height
  } yield (x, y)

  def idx(x: Int, y: Int): Int = {
    if(!inBound(x, y))
      throw new IndexOutOfBoundsException()
    x + y * width
  }

  def inBound(x: Int, y: Int): Boolean =
    x >= 0 && x < width && y >= 0 && y < height

}
