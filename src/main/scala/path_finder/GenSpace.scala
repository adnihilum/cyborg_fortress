package path_finder

import scala.reflect.ClassTag

class GenSpace[CellType: ClassTag] (val width: Int, val height: Int, defaultCell: CellType)
{
  val cells: Array[CellType] = new Array[CellType](width * height)

  def inBound(x: Int, y: Int): Boolean =
    (x >= 0) && (x < width) && (y >= 0) && (y < height)

  def idx(x:Int, y:Int): Int = {
    if(!inBound(x, y))
      throw new IndexOutOfBoundsException
    x + y * width
  }

  def apply(x: Int, y:Int): CellType = cells(idx(x,y))
  def update(x: Int, y:Int, cell:CellType): Unit = cells(idx(x, y)) = cell

  def p(p: Point):CellType = this(p.x, p.y)

  for{
    x <- 0 until width
    y <- 0 until height
  } {
    this(x, y) = defaultCell
  }

}

object GenSpace {
  def apply[CellType:ClassTag](width:Int, height: Int, defaultCell:CellType): GenSpace[CellType] =
    new GenSpace[CellType](width, height, defaultCell)
}
