package path_finder

import main.{Dim, Plane2d, Point, SpaceLike}

import scala.reflect.ClassTag

trait GenSpace[CellType] extends Plane2d {
  def apply(p: Point): CellType
  def update(p: Point, cell:CellType): Unit
}

class GenSpacePersist[CellType: ClassTag](val dim: Dim) extends GenSpace[CellType]
{
  val cells: Array[CellType] = new Array[CellType](dim.width * dim.height)

  def apply(p: Point): CellType = cells(idx(p))
  def update(p: Point, cell:CellType): Unit = cells(idx(p)) = cell
}

object GenSpacePersist {
  def apply[CellType: ClassTag](dim: Dim, defaultCell: CellType): GenSpace[CellType] = {
    val space = new GenSpacePersist[CellType](dim)
    for (p <- space.iterate) {
      space(p) = defaultCell
    }
    space
  }
}
