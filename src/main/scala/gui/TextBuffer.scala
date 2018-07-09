package gui

import java.awt.{Color, Dimension}

import scala.reflect.ClassTag
import scala.swing.Graphics2D
import gui.ConvertableToCharOps._
import main.{Dim, Point, SpaceLike, Tile}

class TextBuffer[A: ConvertableToChar: ClassTag]
( tileset: TileSet,
  space: SpaceLike[Tile],
  private var curPoint: Point,
  val dim: Dim
) extends main.Plane2d {

  def move(newPoint: Point): Unit = {
    curPoint = newPoint
  }

  def deltaMove(dx: Int, dy: Int): Unit =
    deltaMove(Point(dx, dy))

  def deltaMove(dp: Point): Unit = {
    curPoint = curPoint + dp
  }

  def drawIntoBuffer(buffer: Graphics2D): Unit = {
    val pixDim: Dim = tileset.convertCoords(dim).toDim

    buffer.setColor(Color.black)
    buffer.fillRect(0, 0, pixDim.width, pixDim.height)

    val subSpace = space.getSubSpace(curPoint, dim)
    for (p <- iterate) {
      tileset.drawCharToBuff(buffer, subSpace(p).toChar, p)
    }
  }

  def pixDimension: Dimension = {
    import scala.language.implicitConversions

    val pixDim = tileset.convertCoords(dim)
    new Dimension(pixDim.width, pixDim.height)
  }
}
