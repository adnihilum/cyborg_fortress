package path_finder

import cats._
import cats.implicits._
import main.{Point, SpaceLike}

object ShowImplicits {
  implicit val spaceShow: Show[WalkSpace] = Show.show[WalkSpace]((x:WalkSpace) => {
    val y: SpaceLike[Cell] = x
    y.show
  })

  implicit val cellShow: Show[Cell] = Show.show[Cell] {
    case CellEmpty => "."
    case CellFull => "#"
  }

  implicit def SpaceLikeShow[CellType: Show]: Show[SpaceLike[CellType]] =
    Show.show[SpaceLike[CellType]] ((s:SpaceLike[CellType]) => {
      val prefix = s"space ${s.dim.width} x ${s.dim.height}\n"
      val body:String =
        (for{
          y <- 0 until s.dim.height
          x <- 0 until s.dim.width
          p = Point(x, y)
        } yield {
          val cell: CellType = s(p)
          if(p.x == (s.dim.width - 1)) cell.show ++ "\n"
          else cell.show
        }).foldLeft("")(_ ++ _)
      prefix + body
    })

  implicit val pathShow: Show[Path] =
    Show.show[Path] {
      case Path(space, points) =>
        val printSpace = GenSpacePersist[String](space.dim, ".")

        // print space itself
        for (p <- space.iterate)
          printSpace(p) = space(p).show

        // print points of the path
        def getChar(idx: Int) = {
          val chars = ('0' to '9') ++ ('a' to 'z')
          chars(idx % chars.length)
        }

        for ((p, idx) <- points.zipWithIndex) {
          printSpace(p) = {
            if (idx == 0) "S" // this is the start point
            else if (idx == points.length - 1) "E" //this is the end
            else getChar(idx).toString
          }
        }
        printSpace.show
    }
}
