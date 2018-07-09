package path_finder

import cats._
import cats.implicits._
import ShowImplicits._

object Main extends App {
  val space = Space(10, 10)

  for{
    x <- 2 to 3
    y <- 2 to 3
  } yield {
    space(x, y) = CellFull
  }

  space(1,5) = CellFull
  println(space.show)

  val path = Path.find(space, Point(0,0), Point(5,5))
  println(s"found path: \n${path.show}")
}
