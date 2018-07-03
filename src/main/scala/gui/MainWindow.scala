package gui
import scala.swing._

trait MainWindow extends SimpleSwingApplication {
  def top : MainFrame = new MainFrame {
    title = "hello, world!"
    contents = new Button {
      text = "click me"
    }
  }
}
