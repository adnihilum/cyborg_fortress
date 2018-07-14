package main

import common.{Dim, Point}
import gui.{TextBuffer, TileSet}
import gui.ConvertableToCharOps._

import scala.util.Random

object Context {
  val tileSet = new TileSet("/home/user/tmp/Bisasam_16x16.png", 16, 16)
  val space = new Space(Dim(20, 20))
  val textBuffer = new TextBuffer[Tile](tileSet, space, Point(-2, -2), Dim(40, 25))
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
  def spawnCreatureLoop: Unit = {
    val x = rnd.nextInt(space.dim.width)
    val y = rnd.nextInt(space.dim.height)
    try world.spawnCreature(Point(x, y)) catch {
      case e: Throwable =>
        println(s"exception: $e")
        spawnCreatureLoop
    }
  }

  for(_ <- 1 to 10) {
    spawnCreatureLoop
  }
}
