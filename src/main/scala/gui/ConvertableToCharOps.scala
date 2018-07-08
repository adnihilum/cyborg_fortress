package gui

import main.Tile

object ConvertableToCharOps {

  implicit class ConvertableToCharImplicit[A: ConvertableToChar](x: A) {
    def toChar: Char = ConvertableToChar[A](implicitly).toChar(x)
  }

  implicit val convertableTileToChar: ConvertableToChar[Tile] = new ConvertableToChar[Tile] {
    def toChar(t: Tile): Char = {
      t match {
        case Tile.Nothing => 178
        case Tile.Empty => 176
        case _: Tile.Creature => 1
        case Tile.Wall => '#'
      }
    }
  }
}
