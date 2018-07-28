package gui.swing

import java.awt.image.BufferedImage
import common.{Dim, Point}
import gui.ConvertableToCharOps._
import gui.TextBuffer
import main.Context.space
import main.Tile

import scala.swing.Graphics2D

object GuiContextAwt {
  val tileSet = new TileSetAwt("/home/user/tmp/Bisasam_16x16.png", 16, 16)
  val textBuffer = new TextBuffer[Tile, BufferedImage, Graphics2D](tileSet, space, Point(-2, -2), Dim(40, 25))
}
