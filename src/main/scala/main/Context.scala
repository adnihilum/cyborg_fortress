package main

import gui.{TextBuffer, TileSet}
import gui.ConvertableToCharOps._

object Context {
  val tileSet = new TileSet("/home/user/tmp/Bisasam_16x16.png", 16, 16)
  val textBuffer = new TextBuffer[Tile](tileSet, 10, 10)
  textBuffer.fill(new Tile)
}
