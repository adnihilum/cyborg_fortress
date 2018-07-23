package gui.lwjgl

import common.Point
import gui.TileSet
import scala.reflect.ClassTag


class TileSetOpengl(val path: String,
                 val charWidth: Int,
                 val charHeight: Int
                )(implicit val imageClassTag: ClassTag[BufferedImage])
  extends TileSet[BufferedImage, BufferedImage] {

  override protected lazy val image: BufferedImage =
    BufferedImage.fromFile(path)

  def charToImage(char: Char): BufferedImage = {
    val charsWidth = 16
    val charsHeight = 16

    val idx: Int = char.toInt
    def convertOpenglPoint(p: Point): Point =
      Point(p.x, image.height - p.y)
    val pixPoint =
      convertOpenglPoint(
        convertCoords(
          Point(idx % charsWidth, idx / charsWidth)
        )
      )
    image.cut(pixPoint.x, pixPoint.y, charWidth, charHeight)
  }

  def drawCharToBuff(buffer: BufferedImage, char: Char, charPoint: Point): Unit = {
    val pixPoint = convertCoords(charPoint)
    val charImage = charImages(char)
    buffer.paste(pixPoint.x, pixPoint.y, charImage)
  }
}
