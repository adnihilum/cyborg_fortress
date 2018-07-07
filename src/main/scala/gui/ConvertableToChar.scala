package gui

trait ConvertableToChar[A] {
  def toChar(x: A): Char
}

object ConvertableToChar {
  def apply[A: ConvertableToChar]: ConvertableToChar[A] = implicitly
}