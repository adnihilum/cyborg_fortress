package gui

import java.awt.{Color, Dimension}

import common.{Dim, Plane2d, Point, SpaceLike}

import scala.reflect.ClassTag
import scala.swing.Graphics2D
import gui.ConvertableToCharOps._
import main.Tile

class TextBuffer[A: ConvertableToChar: ClassTag, ImageType: ClassTag, BufferType]
( tileset: TileSet[ImageType, BufferType],
  space: SpaceLike[Tile],
  private var curPoint: Point,
  val dim: Dim
) extends Plane2d {

  def move(newPoint: Point): Unit = {
    curPoint = newPoint
  }

  def deltaMove(dx: Int, dy: Int): Unit =
    deltaMove(Point(dx, dy))

  def deltaMove(dp: Point): Unit = {
    curPoint = curPoint + dp
  }

  def drawIntoBuffer(buffer: BufferType): Unit = {
    val subSpace = space.getSubSpace(curPoint, dim)
    for (p <- iterate) {
      tileset.drawCharToBuff(buffer, subSpace(p).toChar, p)
    }

    drawFPS(buffer: BufferType)
  }

  private var drawTimestamp: Long = timestamp
  private def timestamp: Long = System.nanoTime

  def drawFPS(buffer: BufferType): Unit = {
    val newDrawTimestamp = timestamp
    val deltaDrawTimestamp = newDrawTimestamp - drawTimestamp
    val fps: Int = (1.0D / (deltaDrawTimestamp.toDouble / 1000.0D /1000.0D / 1000.0D)).toInt
    val fpsString: String = fps.toString
    val point: Point = Point(dim.width - fpsString.length, 0)
    tileset.drawStringToBuff(buffer, fpsString, point)

    drawTimestamp = newDrawTimestamp
  }

  def pixDimension: Dimension = {
    import scala.language.implicitConversions

    val pixDim = tileset.convertCoords(dim)
    new Dimension(pixDim.width, pixDim.height)
  }
}
