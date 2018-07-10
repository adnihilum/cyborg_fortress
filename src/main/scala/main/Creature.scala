package main

import common.Point

trait Action
object Action {
  case object Up extends Action
  case object Down extends Action
  case object Right extends Action
  case object Left extends Action
  case object NoOp extends Action
}

class Creature(var pos: Point, val tile: Tile) {
  def step(world: World): Action = {
    val actions: Array[Action] = Array(
      Action.Up,
      Action.Down,
      Action.Right,
      Action.Left,
      Action.NoOp
    )
    val idx = Context.rnd.nextInt(actions.length)
    actions(idx)
  }
}
