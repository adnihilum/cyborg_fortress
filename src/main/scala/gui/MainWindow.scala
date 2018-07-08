package gui

import scala.swing._
import scala.swing.event.{Key, KeyPressed}
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
          key.key match {
            case Key.Left =>
              textBuffer.deltaMove(-1, 0)
            case Key.Right =>
              textBuffer.deltaMove(1, 0)
            case Key.Up =>
              textBuffer.deltaMove(0, -1)
            case Key.Down =>
              textBuffer.deltaMove(0, 1)
          }

          //println(key.key)
          repaint()
      }
      focusable = true
      requestFocus()
    }
  }
}
