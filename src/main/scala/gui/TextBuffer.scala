package gui

import java.awt.{Color, Dimension}
import scala.reflect.ClassTag
import scala.swing.Graphics2D
import gui.ConvertableToCharOps._

class TextBuffer[A: ConvertableToChar: ClassTag](tileset: TileSet, width: Int, height: Int) {

  val buff: Array[A] = Array.ofDim[A](width * height)

  def idx(x: Int, y: Int): Int = {
    if(x < 0 || x >= width || y < 0 || y >= height)
      throw new IndexOutOfBoundsException()
    x + y * width
  }

  def apply(x: Int, y: Int): A =
    buff(idx(x, y))

  def update(x: Int, y: Int, char: A): Unit =
    buff(idx(x, y)) = char

  def drawIntoBuffer(buffer: Graphics2D): Unit = {
    val (pixWidth, pixHeight) = tileset.convertCoords(width, height)

    buffer.setColor(Color.black)
    buffer.fillRect(0, 0, pixWidth, pixHeight)

    for ((x, y) <- iterate) {
      tileset.drawCharToBuff(buffer, this(x, y).toChar, x, y)
    }
  }

  def iterate: Iterator[(Int, Int)] = for {
    x <- (0 until width).toIterator
    y <- 0 until height
  } yield (x, y)

  def fill(tile: A): Unit = {
    for ((x, y) <- iterate) {
      this(x, y) = tile
    }
  }

  def pixDimension: Dimension = {
    val (pixWidth, pixHeight) = tileset.convertCoords(width, height)
    new Dimension(pixWidth, pixHeight)
  }

}
