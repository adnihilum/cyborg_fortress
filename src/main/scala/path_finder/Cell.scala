package path_finder

sealed trait Cell

object Cell

object CellEmpty extends Cell
object CellFull extends Cell
