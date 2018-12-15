package main.creatures

import common.Point
import main.{Action, Creature, Tile, World}

import scala.util.Random

case class MadWalker(override var pos:Point)(implicit override val world: World) extends Creature {
  override val tile = Tile.Zombi

  val rnd = new Random

  def step: Action.Value = rnd.shuffle(Action.values.toList).head
}
