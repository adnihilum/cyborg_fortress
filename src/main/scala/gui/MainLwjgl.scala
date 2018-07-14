package gui

import org.lwjgl._
import org.lwjgl.glfw.Callbacks._
import org.lwjgl.glfw.GLFW._
import org.lwjgl.glfw._
import org.lwjgl.opengl.GL11._
import org.lwjgl.opengl._
import org.lwjgl.system.MemoryUtil._


object MainLwjgl extends App {

  private val WIDTH  = 800
  private val HEIGHT = 600

  def run() {
    try {
      GLFWErrorCallback.createPrint(System.err).set()

      val window = init()
      loop(window)

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

    val window = glfwCreateWindow(WIDTH, HEIGHT, "LWJGL in Scala", NULL, NULL)
    if (window == NULL)
      throw new RuntimeException("Failed to create the GLFW window")

    glfwSetKeyCallback(window, keyHandler _)

    val vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor())

    glfwSetWindowPos (
      window,
      (vidMode. width() -  WIDTH) / 2,
      (vidMode.height() - HEIGHT) / 2
    )

    glfwMakeContextCurrent(window)
    glfwSwapInterval(1)
    glfwShowWindow(window)

    window
  }

  private def loop(window: Long) {
    GL.createCapabilities()

    glClearColor(0f, 0f, 0f, 0f)

    while (!glfwWindowShouldClose(window)) {
      glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT)
      render()
      glfwSwapBuffers(window)
      glfwPollEvents()
    }
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

  private var sp: Float = .5f
  private def render(): Unit = {
    glColor3f(0.0f, 1.0f, 0.0f)
    glBegin(GL_QUADS)
    glVertex2f(-sp, -sp)
    glVertex2f(sp, -sp)
    glVertex2f(sp, sp)
    glVertex2f(-sp, sp)
    glEnd()

  }

  run()
}