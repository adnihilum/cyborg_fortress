package gui.lwjgl

import common.{Dim, Point}
import gui.TextBuffer
import main.Context.space
import main.Tile
import gui.ConvertableToCharOps._

object GuiContextOpengl {
  val tileSet = new TileSetOpengl("/home/user/tmp/Bisasam_16x16.png", 16, 16)
  val textBuffer = new TextBuffer[Tile, BufferedImage, BufferedImage](tileSet, space, Point(-2, -2), Dim(40, 25))
}
