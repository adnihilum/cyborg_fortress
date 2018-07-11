package path_finder


import cats._
import cats.implicits._
import common.Dim
import org.scalatest.FlatSpec
import path_finder.ShowImplicits._

object WalkSpaceTest {
  def cleanSpaceString(str: String): String =
    str.filter(x => x == '.' | x == '#' | x == 'S' | x == 'E')
}

class WalkSpaceTest extends FlatSpec {
  import WalkSpaceTest.cleanSpaceString

  "a space in a text form" should "correctly be parsed" in {
    assert(
      WalkSpace.fromString(Dim(4,4),
        """
          |####
          |....
          |SESE
          |####
        """.stripMargin
      ).toTextForm ==
        cleanSpaceString(
        """
          |####
          |....
          |....
          |####""".stripMargin)
    )
  }
}
