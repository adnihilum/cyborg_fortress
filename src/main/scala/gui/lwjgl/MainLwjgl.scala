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

object MainLwjgl extends App {
  private val width  = 800
  private val height = 600

  var textBuffer: TextBuffer[Tile, Symbol, Unit] = _ // TODO:  fix that, there shouldn't be any var

  def run() {
    try {
      GLFWErrorCallback.createPrint(System.err).set()

      val window = init()
      initRender()

      val tileSet = new TileSetOpengl("/home/user/tmp/Bisasam_16x16.png", 16, 16)
      textBuffer = new TextBuffer[Tile, Symbol, Unit](tileSet, space, Point(-2, -2), Dim(40, 25))

      Context.simulation.start {
        renderMain(window)
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

    val window = glfwCreateWindow(width, height, "LWJGL in Scala", NULL, NULL)
    if (window == NULL)
      throw new RuntimeException("Failed to create the GLFW window")

    glfwSetKeyCallback(window, keyHandler _)

    val vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor())

    glfwSetWindowPos (
      window,
      (vidMode. width() -  width) / 2,
      (vidMode.height() - height) / 2
    )

    glfwMakeContextCurrent(window)
    glfwSwapInterval(1)
    glfwShowWindow(window)

    window
  }

  private def renderMain(window: Long): Unit = {
    if(glfwWindowShouldClose(window))
      throw new Exception("widow was closed")

    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT)
    render()
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
    glOrtho(0.0, width, 0.0, height, -width, height)
    glMatrixMode(GL_MODELVIEW)
  }

  private def render(): Unit = {
    textBuffer.drawIntoBuffer(() )
  }

  run()
}
