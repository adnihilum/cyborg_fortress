package gui.swing

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File

import common.Point
import gui.TileSet
import javax.imageio.ImageIO

import scala.reflect.ClassTag
import scala.swing.Graphics2D

class TileSetAwt(val path: String,
                 val charWidth: Int,
                 val charHeight: Int
                )(implicit val imageClassTag: ClassTag[BufferedImage]) extends TileSet[BufferedImage, Graphics2D] {
  protected val image: BufferedImage = ImageIO.read(new File(path))

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
}
