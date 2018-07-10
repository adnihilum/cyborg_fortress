package path_finder

sealed trait Cell

object Cell {
  object Empty extends Cell
  object Full extends Cell
}
