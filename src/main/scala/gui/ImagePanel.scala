package gui

import main.Context._
import scala.swing.{Graphics2D, Panel}

class ImagePanel extends Panel {
  override def paintComponent(g: Graphics2D): Unit = {
    textBuffer.drawIntoBuffer(g)
  }
}
