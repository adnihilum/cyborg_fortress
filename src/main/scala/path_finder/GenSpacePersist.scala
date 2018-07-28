package path_finder

import common.{Dim, Point, SpaceLike}
import scala.reflect.ClassTag

class GenSpacePersist[CellType: ClassTag](val dim: Dim) extends SpaceLike[CellType]
{
  val cells: Array[CellType] = new Array[CellType](dim.width * dim.height)

  def apply(p: Point): CellType = cells(idx(p))
  def update(p: Point, cell:CellType): Unit = cells(idx(p)) = cell
}

object GenSpacePersist {
  def apply[CellType: ClassTag](dim: Dim, defaultCell: CellType): SpaceLike[CellType] = {
    val space = new GenSpacePersist[CellType](dim)
    for (p <- space.iterate) {
      space(p) = defaultCell
    }
    space
  }
}