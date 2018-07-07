package gui

import main.Tile

object ConvertableToCharOps {

  implicit class ConvertableToCharImplicit[A: ConvertableToChar](x: A) {
    def toChar: Char = ConvertableToChar[A](implicitly).toChar(x)
  }

  implicit val convertableTileToChar: ConvertableToChar[Tile] = new ConvertableToChar[Tile] {
    def toChar(t: Tile): Char = 'A'
  }
}
