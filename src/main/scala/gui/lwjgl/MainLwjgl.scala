package gui.lwjgl

import org.lwjgl.glfw.Callbacks._
import org.lwjgl.glfw.GLFW._
import org.lwjgl.glfw._
import org.lwjgl.opengl.GL11._
import org.lwjgl.opengl._
import org.lwjgl.system.MemoryUtil._


object MainLwjgl extends App {

  private val width  = 800
  private val height = 600

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

  private def loop(window: Long) {
    initRender()
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

  val texId: Int = 0
  private def next2powerValue(v: Int ): Int =
    1 << (math.log(v.toDouble) / math.log(2.0D)).ceil.toInt

  val widthTex: Int = next2powerValue(width)
  val widthTexD: Double = width.toDouble / widthTex.toDouble
  val heightTex: Int = next2powerValue(height)
  val heightTexD: Double = height.toDouble / heightTex.toDouble

  val tilesetImage = BufferedImage.fromFile("/home/user/tmp/Bisasam_16x16.png")
  val texture = BufferedImage.blank(widthTex, heightTex)

  def initRender(): Unit = {
    GL.createCapabilities()

    glClearColor(0f, 0f, 0f, 0f)

    // init projection
    glMatrixMode(GL_PROJECTION)
    glOrtho(0.0, width, 0.0, height, -width, height)
    glMatrixMode(GL_MODELVIEW)

    //init texture
    val texId = glGenTextures()
    glBindTexture(GL_TEXTURE_2D, texId)
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT)
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT)
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST)
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST)
    glTexEnvi(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_DECAL)

    texture.paste(0, 0, tilesetImage)

    glTexImage2D(
      GL_TEXTURE_2D,
      0,
      GL_RGBA8,
      widthTex,
      heightTex,
      0,
      GL_RGBA,
      GL_UNSIGNED_BYTE,
      texture.buffer)

    glEnable(GL_TEXTURE_2D)
  }

  private def render(): Unit = {
    def renderTexture() = {
      glTexImage2D(
        GL_TEXTURE_2D,
        0,
        GL_RGBA8,
        widthTex,
        heightTex,
        0,
        GL_RGBA,
        GL_UNSIGNED_BYTE,
        texture.buffer)
    }

    def renderPoligon() = {
      glColor3f(0.0f, 0.0f, 0.0f)
      glBegin(GL_QUADS)
      glTexCoord2d(0, 0)
      glVertex2d(0, 0)
      glTexCoord2d(widthTexD, 0)
      glVertex2f(width, 0)
      glTexCoord2d(widthTexD, heightTexD)
      glVertex2f(width, height)
      glTexCoord2d(0, heightTexD)
      glVertex2f(0, height)
      glEnd()
    }

    renderTexture()
    renderPoligon()
  }

  run()
}
