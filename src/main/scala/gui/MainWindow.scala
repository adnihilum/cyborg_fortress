package gui
import java.awt.Rectangle
import java.awt.Color
import java.awt.geom.AffineTransform
import java.awt.image.BufferedImage
import java.io.File

import scala.swing._
import scala.swing.event.{ButtonClicked, KeyPressed}
import javax.imageio.ImageIO

import scala.language.implicitConversions
import scala.reflect.ClassTag

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
    buffer.drawRenderedImage(charImage, AffineTransform.getTranslateInstance(x, y))
  }
}

class Tile


trait ConvertableToChar[A] {
  def toChar(x: A): Char
}

object ConvertableToChar {
  def apply[A: ConvertableToChar]: ConvertableToChar[A] = implicitly
}


object ConvertableToCharOps {

  implicit class ConvertableToCharImplicit[A: ConvertableToChar](x: A) {
    def toChar: Char = ConvertableToChar[A](implicitly).toChar(x)
  }

  implicit val convertableTileToChar: ConvertableToChar[Tile] = new ConvertableToChar[Tile] {
    def toChar(t: Tile): Char = 'X'
  }
}


import ConvertableToCharOps._

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
    for ((x, y) <- iterate) {
      tileset.drawCharToBuff(buffer, this(x, y).toChar, x, y)
    }
  }

  val iterate: Iterator[(Int, Int)] = for {
    x <- (0 until width).toIterator
    y <- 0 until width
  } yield (x, y)

  def fill(tile: A): Unit = {
    for ((x, y) <- iterate) {
      this(x, y) = tile
    }
  }

}

object Context {
  val tileSet = new TileSet("/home/user/tmp/Bisasam_16x16.png", 16, 16)
  val textBuffer = new TextBuffer[Tile](tileSet, 10, 10)
  textBuffer.fill(new Tile)
}

class ImagePanel extends Panel {
  override def paintComponent(g: Graphics2D): Unit = {
    import Context._
    g.setBackground(Color.black)
    g.setColor(Color.blue)
    g.fillRect(0, 0, 100, 100)

    textBuffer.drawIntoBuffer(g)
    println(s"g = 1$g")
  }
}

trait MainWindow extends SimpleSwingApplication {
  def top : MainFrame = new MainFrame {
    title = "hello, world!"
    bounds = new Rectangle(100, 100, 100, 100)

    val label: Label = new Label {
      text = "hello, world!"
    }

    contents = new ImagePanel { //(Orientation.Horizontal) {
      //contents = "123"//label

//      listenTo(keys)
//      reactions += {
//        case key:KeyPressed =>
//          println(key.key)
//          //label.text = key.key.toString
//      }
//      focusable = true
//      requestFocus()
    }
  }
}
