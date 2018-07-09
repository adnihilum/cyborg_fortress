package path_finder

import main.{Dim, Plane2d, Point, SpaceLike}

import scala.reflect.ClassTag

class GenSpace[CellType: ClassTag](val dim: Dim) extends Plane2d
{
  val cells: Array[CellType] = new Array[CellType](dim.width * dim.height)

  def apply(p: Point): CellType = cells(idx(p))
  def update(p: Point, cell:CellType): Unit = cells(idx(p)) = cell
}

object GenSpace {
  def apply[CellType: ClassTag](dim: Dim, defaultCell: CellType): GenSpace[CellType] = {
    val space = new GenSpace[CellType](dim)
    for (p <- space.iterate) {
      space(p) = defaultCell
    }
    space
  }
}
