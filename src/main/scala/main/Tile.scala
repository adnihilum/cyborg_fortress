package main

sealed trait Tile

object Tile {
  object Nothing extends Tile
  object Empty extends Tile
  object Wall extends Tile
  case class Creature(name: String) extends Tile

  object Walker extends Tile
  object Zombi extends Tile
}
