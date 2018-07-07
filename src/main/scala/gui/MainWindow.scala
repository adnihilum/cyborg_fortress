package gui

import scala.swing._
import scala.swing.event.KeyPressed
import scala.language.implicitConversions
import main.Context._

trait MainWindow extends SimpleSwingApplication {
  def top: MainFrame = new MainFrame {
    title = "hello, world!"
    contents = new ImagePanel {
      maximumSize = textBuffer.pixDimension
      preferredSize = maximumSize

      //TODO:  move this code
      listenTo(keys)
      reactions += {
        case key:KeyPressed =>
          println(key.key)
      }
      focusable = true
      requestFocus()
    }
  }
}
