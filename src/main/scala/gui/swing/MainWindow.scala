package gui.swing

import GuiContextAwt._
import main.Context
import scala.language.implicitConversions
import scala.swing._
import scala.swing.event.{Key, KeyPressed}

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
            case _ =>
          }

          repaint()
      }
      focusable = true
      requestFocus()

      Context.simulation.start {
        repaint()
      }
    }
  }
}
