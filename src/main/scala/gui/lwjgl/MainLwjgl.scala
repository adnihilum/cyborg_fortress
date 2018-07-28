package gui.lwjgl

import common.{Dim, Point}
import gui.TextBuffer
import org.lwjgl.glfw.Callbacks._
import org.lwjgl.glfw.GLFW._
import org.lwjgl.glfw._
import org.lwjgl.opengl.GL11._
import org.lwjgl.opengl._
import org.lwjgl.system.MemoryUtil._
import main.{Context, Tile}
import main.Context.space
import gui.ConvertableToCharOps._
import common.DimOps._

object MainLwjgl extends App {
  val textDim: Dim = Dim(40, 25)
  val dim: Dim = textDim * 16

  type TextBufferOpengl = TextBuffer[Tile, Symbol, Unit]

  def run(): Unit = {
    try {
      GLFWErrorCallback.createPrint(System.err).set()

      val window = init()
      initRender()
      val tileSet = new TileSetOpengl("/home/user/tmp/Bisasam_16x16.png", 16, 16)
      val textBuffer = new TextBuffer[Tile, Symbol, Unit](tileSet, space, Point(-2, -2), textDim)

      Context.simulation.start { () =>
        renderMain(window, textBuffer)
      }.join()

      glfwFreeCallbacks(window)
      glfwDestroyWindow(window)
    } finally {
      glfwTerminate() // destroys all remaining windows, cursors, etc...
      glfwSetErrorCallback(null).free()
    }
  }

  private def init(): Long = {
    if (!glfwInit())
      throw new IllegalStateException("Unable to initialize GLFW")

    glfwDefaultWindowHints()
    glfwWindowHint(GLFW_VISIBLE,   GLFW_FALSE) // hiding the window
    glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE) // window resizing not allowed

    val window = glfwCreateWindow(dim.width, dim.height, "LWJGL in Scala", NULL, NULL)
    if (window == NULL)
      throw new RuntimeException("Failed to create the GLFW window")

    glfwSetKeyCallback(window, keyHandler _)

    glfwMakeContextCurrent(window)
    glfwSwapInterval(1)
    glfwShowWindow(window)

    window
  }

  private def renderMain(window: Long, textBuffer: TextBufferOpengl): Unit = {
    if(glfwWindowShouldClose(window))
      throw new Exception("widow was closed")
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT)

    textBuffer.drawIntoBuffer(())

    glfwSwapBuffers(window)
    glfwPollEvents()
  }

  private def keyHandler (window: Long,
                          key: Int,
                          scanCode: Int,
                          action: Int,
                          mods: Int
                         ): Unit = {
    if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
      glfwSetWindowShouldClose(window, true)
  }

  def initRender(): Unit = {
    GL.createCapabilities()
    glClearColor(0f, 0f, 0f, 0f)

    // init projection
    glMatrixMode(GL_PROJECTION)
    glOrtho(0.0, dim.width, 0.0, dim.height, -dim.width, dim.height)
    glMatrixMode(GL_MODELVIEW)
  }

  run()
}
