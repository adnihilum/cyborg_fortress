package benchmark

import common.{Dim, Point}
import main.{Space, Tile}
import path_finder.{Path, WalkSpace}

import scala.util.Random

object PathFinding extends App {
  val dim = Dim(20, 20)

  for( step <- 1 to 1000 ) {
//    println("#" * 20)
//    println("#" * 5 + s" step: $step")
//    println("#" * 20)

    val space = new Space(dim)
    val rnd = new Random

    // build walls
    for(p <- space.iterate) {
      if (rnd.nextFloat() > 0.8) {
        space(p) = Tile.Wall
      }
    }

    // find paths
    def canWalk(p: Point, t: Tile): Boolean =
      t match {
        case Tile.Empty => true
        case _ => false
      }

    def getRandomPoint: Point =
      Point(
        rnd.nextInt(dim.width),
        rnd.nextInt(dim.height)
      )

    val walkSpace = WalkSpace.fromOtherSpace(space, canWalk)
    val start = getRandomPoint
    val goal = getRandomPoint
    val path = Path.findP(walkSpace, start, goal)
    //if(path.isDefined)println(path.get.show)

  }
}
