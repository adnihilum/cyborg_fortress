package main

import common.{Dim, Point}
import scala.util.Random

object Context {
  val space = new Space(Dim(100, 100))
  val world = new World(space)
  val simulation = new Simulation(world)
  val rnd = new Random

  // build walls
  for(p <- space.iterate) {
    if (rnd.nextFloat() > 0.8) {
      space(p) = Tile.Wall
    }
  }

  // spawn creatures
  def spawnCreatureLoop(): Unit = {
    val x = rnd.nextInt(space.dim.width)
    val y = rnd.nextInt(space.dim.height)
    try world.spawnCreature(Point(x, y)) catch {
      case e: Throwable =>
        println(s"exception: $e")
        spawnCreatureLoop()
    }
  }

  for(_ <- 1 to 100) {
    spawnCreatureLoop()
  }
}
