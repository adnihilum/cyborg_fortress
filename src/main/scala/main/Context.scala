package main

import gui.{TextBuffer, TileSet}
import gui.ConvertableToCharOps._
import scala.util.Random

object Context {
  val tileSet = new TileSet("/home/user/tmp/Bisasam_16x16.png", 16, 16)
  val space = new Space(100, 100)
  val textBuffer = new TextBuffer[Tile](tileSet, space, -2, -2, 40, 25)

  // populate space
  val rnd = new Random
  for((x, y) <- space.iterate) {
    if (rnd.nextFloat() > 0.8) {
      space(x, y) = Tile.Wall
    }
  }
}
