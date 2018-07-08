package gui

import java.awt.{Color, Dimension}
import scala.reflect.ClassTag
import scala.swing.Graphics2D
import gui.ConvertableToCharOps._
import main.SpaceLike

class TextBuffer[A: ConvertableToChar: ClassTag]
( tileset: TileSet,
  space: SpaceLike,
  private var curX: Int,
  private var curY: Int,
  val width: Int,
  val height: Int
) extends main.Plane2d {

  def move(newX: Int, newY: Int): Unit = {
    curX = newX
    curY = newY
  }

  def drawIntoBuffer(buffer: Graphics2D): Unit = {
    val (pixWidth, pixHeight) = tileset.convertCoords(width, height)

    buffer.setColor(Color.black)
    buffer.fillRect(0, 0, pixWidth, pixHeight)

    val subSpace = space.getSubSpace(curX, curY, width, height)
    for ((x, y) <- iterate) {
      tileset.drawCharToBuff(buffer, subSpace(x, y).toChar, x, y)
    }
  }

  def pixDimension: Dimension = {
    val (pixWidth, pixHeight) = tileset.convertCoords(width, height)
    new Dimension(pixWidth, pixHeight)
  }
}
