package path_finder

import cats._
import cats.implicits._
import path_finder.ShowImplicits._

import common.{Dim, Point}
import org.scalatest.FlatSpec

class PathTest extends FlatSpec {
  import WalkSpaceTest.cleanSpaceString
  def pathArgsFromString(width: Int, height: Int, str: String): (WalkSpace, Point, Point) = {
    val cleanString = cleanSpaceString(str)
    def getCoords (c: Char): Point = {
      val idx:Int = cleanString.indexOf(c)
      Point(idx % width, idx / width)
    }
    (WalkSpace.fromString(Dim(width, height), cleanString),
      getCoords('S'),
      getCoords('E'))
  }

  "a string represintation of a path args" should "be correctly parsed" in {
    val (space, startP, endP) = pathArgsFromString(4, 4,
    """
      |####
      |#S..
      |..##
      |...E
    """.stripMargin)

    assert(space.toTextForm ==
      cleanSpaceString(
        """
          |####
          |#...
          |..##
          |....
        """.stripMargin)
    )
    assert(startP == Point(1, 1))
    assert(endP == Point(3, 3))

  }
  "a path" should "be found" in {
    val spaceString =
      """
        |..#....
        |.#.##..
        |.S...#E
        |.####..
        |.......
      """.stripMargin
    val (space, start, end) = pathArgsFromString(7, 5, spaceString)
    val path = Path.find(space, start, end).get

    println(path.show)
    println(path.points(0))
    println(path.points(1))

  }

  it should "be empty when start and stop points are the same" in {
    val spaceString =
      """
        |..#....
        |.#.##..
        |.S...#E
        |.####..
        |.......
      """.stripMargin
    val point = Point(0, 2)
    val space = WalkSpace.fromString(Dim(7, 5), spaceString)
    val path = Path.find(space, start = point, goal = point)
    assert(path.isEmpty)
  }

  it should "not be found whe both start and goal points are not accessable" in {
    val spaceString =
      """
        |.......
        |.#.#...
        |..#....
        |.......
        |.......
      """.stripMargin
    val start = Point(1, 1)
    val goal = Point(3, 1)
    val space = WalkSpace.fromString(Dim(7, 5), spaceString)
    val path = Path.find(space, start, goal)
    assert(path.isEmpty)
  }
}
