package gui

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File

import common.Point
import javax.imageio.ImageIO

import scala.swing.Graphics2D

class TileSet(path: String, charWidth: Int, charHeight: Int) {
  private val image = ImageIO.read(new File(path))
  val charImages: Array[BufferedImage] = Array.ofDim(16 * 16)

  for(c <- 0 until 256) {
    charImages(c) = charToImage(c.toChar)
  }

  def convertCoords(p: Point): Point =
    Point(p.x * charWidth, p.y * charHeight)

  def charToImage(char: Char): BufferedImage = {
    val idx: Int = char.toInt
    val pixPoint = convertCoords(Point(idx % 16, idx / 16))
    image.getSubimage(pixPoint.x, pixPoint.y, charWidth, charHeight)
  }

  def drawCharToBuff(buffer: Graphics2D, char: Char, charPoint: Point): Unit = {
    val pixPoint = convertCoords(charPoint)
    val charImage = charImages(char)
    val transform = buffer.getTransform


    buffer.setColor(Color.black)
    buffer.fillRect(pixPoint.x, pixPoint.y, charWidth, charHeight)

    transform.translate(pixPoint.x, pixPoint.y)
    buffer.drawRenderedImage(charImage, transform)
  }

  def drawStringToBuff(buffer: Graphics2D, string: String, charPoint: Point): Unit = {
    for {
      (c, idx) <- string.zipWithIndex
      x = charPoint.x + idx
      y = charPoint.y
      p = Point(x, y)
    } {
      drawCharToBuff(buffer, c, p)
    }
  }
}
