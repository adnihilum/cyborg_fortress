package gui.lwjgl

import java.io.File
import javax.imageio.ImageIO

case class BufferedImage(buffer: Array[Int],
                         width: Int,
                         height: Int) {
  def inBound(x: Int, y: Int): Boolean =
    (x >= 0) && (x < width) && (y >= 0) && (y < height)

  def apply(x: Int, y: Int): Int =
    if (inBound(x, y)) buffer(y * width + x)
    else throw new IndexOutOfBoundsException

  def update(x: Int, y: Int, value: Int): Unit =
    if (inBound(x, y)) buffer(y * width + x) = value
    else throw new IndexOutOfBoundsException

  def cut(cutX: Int, cutY: Int, cutWidth: Int, cutHeight: Int): BufferedImage =
    BufferedImage({
      for {
        x <- cutX until (cutX + cutWidth)
        y <- cutY until (cutY + cutHeight)
      } yield
        if (inBound(x, y)) this(x, y)
        else 0
    }.toArray,
      cutWidth,
      cutHeight
    )

  def paste(offX: Int, offY: Int, that: BufferedImage): Unit = {
    for {
      x <- 0 until that.width
      y <- 0 until that.height
      destX = x + offX
      destY = y + offY
    } {
      if (inBound(destX, destY))
        this(destX, destY) = that(x, y)
    }
  }

}

object BufferedImage {
  def fromFile(filename: String): BufferedImage = {
    val image = ImageIO.read(new File(filename))
    val width = image.getWidth
    val height = image.getHeight

    val rawPixels: Array[Int] = image.getRGB(0, 0, width, height, null, 0, width)

    def invertPixels(oldPixels: Array[Int]): Array[Int] =
      oldPixels.grouped(width).toList.reverse.flatten.toArray
    new BufferedImage(invertPixels(rawPixels), width, height)
  }

  def blank(width: Int, height: Int): BufferedImage = {
    val pixels: Array[Int] = Array.fill(width * height)(0)
    new BufferedImage(pixels, width, height)
  }
}
