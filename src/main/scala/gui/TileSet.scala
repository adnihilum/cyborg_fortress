package gui

import java.awt.image.BufferedImage
import java.io.File

import javax.imageio.ImageIO

import scala.swing.Graphics2D

class TileSet(path: String, charWidth: Int, charHeight: Int) {
  private val image = ImageIO.read(new File(path))

  def convertCoords(x: Int, y: Int): (Int, Int) = {
    (x * charWidth, y * charHeight)
  }

  def charToImage(char: Char): BufferedImage = {
    val idx: Int = char.toInt
    val (x, y) = convertCoords(idx % 16, idx / 16)
    image.getSubimage(x, y, charWidth, charHeight)
  }

  def drawCharToBuff(buffer: Graphics2D, char: Char, charX: Int, charY: Int): Unit = {
    val (x, y) = convertCoords(charX, charY)
    val charImage = charToImage(char)
    val transform = buffer.getTransform
    transform.translate(x, y)
    buffer.drawRenderedImage(charImage, transform)
  }
}
