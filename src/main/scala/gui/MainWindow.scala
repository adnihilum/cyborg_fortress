package gui
import scala.swing._
import scala.swing.event.{ButtonClicked, KeyPressed}

trait MainWindow extends SimpleSwingApplication {
  def top : MainFrame = new MainFrame {
    title = "hello, world!"

    val label: Label = new Label {
      text = "hello, world!"
    }

    contents = new BoxPanel(Orientation.Horizontal) {
      contents += label

      listenTo(keys)
      reactions += {
        case key:KeyPressed =>
          label.text = key.key.toString
      }
      focusable = true

      requestFocus()
    }



  }
}
