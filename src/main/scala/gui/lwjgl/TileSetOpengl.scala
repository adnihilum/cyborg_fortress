package gui.lwjgl

import common.Point
import gui.TileSet
import scala.reflect.ClassTag
import org.lwjgl._
import opengl.GL11._


class TileSetOpengl(val path: String,
                    val charWidth: Int,
                    val charHeight: Int
                   )(implicit val imageClassTag: ClassTag[Symbol])
  extends TileSet[Symbol, Unit] {

  lazy val textureImage: BufferedImage =
    BufferedImage.fromFile(path)

  def next2powerValue(v: Int ): Int =
    1 << (math.log(v.toDouble) / math.log(2.0D)).ceil.toInt

  def pixX2texX(x: Int): Double = x.toDouble / next2powerValue(textureImage.width).toDouble
  def pixY2texY(y: Int): Double = y.toDouble / next2powerValue(textureImage.height).toDouble

  lazy val textureId: Int = {
    val texId = glGenTextures()
    glBindTexture(GL_TEXTURE_2D, texId)
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT)
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT)
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST)
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST)
    glTexEnvi(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_DECAL)


    val widthTex: Int = next2powerValue(textureImage.width)
    val heightTex: Int = next2powerValue(textureImage.height)

    glTexImage2D(
      GL_TEXTURE_2D,
      0,
      GL_RGBA8,
      widthTex,
      heightTex,
      0,
      GL_RGBA,
      GL_UNSIGNED_BYTE,
      textureImage.buffer)

    glEnable(GL_TEXTURE_2D)
    texId
  }

  def charToImage(char: Char): Symbol = {
    val charsWidth = 16
    val charsHeight = 16

    val idx: Int = char.toInt
    def convertOpenglPoint(p: Point): Point =
      Point(p.x, charsHeight - p.y - 1)
    val pixPoint =

        convertCoords(convertOpenglPoint(
          Point(idx % charsWidth, idx / charsWidth)
        )
      )
    Symbol(
      textureId,
      pixX2texX(pixPoint.x), pixY2texY(pixPoint.y),
      pixX2texX(charWidth), pixY2texY(charHeight)
    )
  }

  def drawCharToBuff(buffer: Unit, char: Char, charPoint: Point): Unit = {
    val pixPoint = convertCoords(charPoint)
    val symbol = charImages(char)
    glBindTexture(GL_TEXTURE_2D, textureId)
    symbol.render(pixPoint.x, pixPoint.y, charWidth, charHeight)
  }
}
