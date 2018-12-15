package main

import common.Point
import main.creatures.{MadWalker, RandomWalker}

import scala.util.Random

trait Creature {
  val world: World
  var pos: Point
  val tile: Tile = Tile.Creature("default")

  def step: Action.Value
}

object Creature {
  val rnd = new Random
  def apply(pos: Point)(implicit world: World): Creature =
    rnd.nextInt(2) match {
      case 0 => RandomWalker(pos)
      case 1 => MadWalker(pos)
    }
}
