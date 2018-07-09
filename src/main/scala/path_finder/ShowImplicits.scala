package path_finder

import cats._
import cats.implicits._

object ShowImplicits {
  // for Space
  implicit val spaceShow: Show[Space] = Show.show[Space]((x:Space) => {
    val y:GenSpace[Cell] = x
    y.show
  })

  // for Cell
  implicit val cellShow: Show[Cell] = Show.show[Cell] {
    case CellEmpty => "."
    case CellFull => "#"
  }

  // for GenSpace
  implicit def genSpaceShow[CellType: Show]:Show[GenSpace[CellType]] =
    Show.show[GenSpace[CellType]] ((s:GenSpace[CellType]) => {
      val prefix = s"space ${s.width} x ${s.height}\n"
      val body:String =
        (for{
          y <- 0 until s.height
          x <- 0 until s.width
        } yield {
          val cell:CellType = s.cells(s.idx(x, y))
          if(x == (s.width - 1)) cell.show ++ "\n"
          else cell.show
        }).foldLeft("")(_ ++ _)
      prefix + body
    })

  implicit val pathShow: Show[Path] =
    Show.show[Path] {
      case Path(space, points) =>
        val printSpace = GenSpace[String](space.width, space.height, ".")

        // print space itself
        for {
          x <- 0 until space.width
          y <- 0 until space.height
        } yield {
          printSpace(x, y) = space(x, y).show
        }

        // print points of the path
        def getChar(idx: Int) = {
          val chars = ('0' to '9') ++ ('a' to 'z')
          chars(idx % chars.length)
        }

        for {(p, idx) <- points.zipWithIndex} yield {
          printSpace(p.x, p.y) = {
            if (idx == 0) "S" // this is the start point
            else if (idx == points.length - 1) "E" //this is the end
            else getChar(idx).toString
          }
        }
        printSpace.show
    }
}
