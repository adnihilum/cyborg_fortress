package main

import gui.{TextBuffer, TileSet}
import gui.ConvertableToCharOps._
import scala.util.Random

object Context {
  val tileSet = new TileSet("/home/user/tmp/Bisasam_16x16.png", 16, 16)
  val space = new Space(Dim(100, 100))
  val textBuffer = new TextBuffer[Tile](tileSet, space, Point(-2, -2), Dim(40, 25))

  // populate space
  val rnd = new Random
  for(p <- space.iterate) {
    if (rnd.nextFloat() > 0.8) {
      space(p) = Tile.Wall
    }
  }
}
