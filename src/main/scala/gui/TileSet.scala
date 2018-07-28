package gui

import common.Point
import scala.reflect.ClassTag

trait TileSet[ImageType, BufferType] {
  implicit val imageClassTag: ClassTag[ImageType]

  val path: String
  val charWidth: Int
  val charHeight: Int

  val charImages: Array[ImageType] = Array.ofDim[ImageType](16 * 16)

  for(c <- 0 until 256) {
    charImages(c) = charToImage(c.toChar)
  }

  def convertCoords(p: Point): Point =
    Point(p.x * charWidth, p.y * charHeight)

  def charToImage(char: Char): ImageType
  def drawCharToBuff(buffer: BufferType, char: Char, charPoint: Point): Unit

  def drawStringToBuff(buffer: BufferType, string: String, charPoint: Point): Unit = {
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
