package main

import scala.collection.mutable

class World(val space: Space) {
  var creatures: mutable.Set[Creature] = mutable.Set()

  def spawnCreature (pos: Point, tile: Tile = main.Tile.Creature("default")): Unit = {
    if(space(pos) != Tile.Empty) throw new Exception("fail to spawn creature")
    val creature: Creature = new Creature(pos, tile)
    space(pos) = tile
    creatures += creature
  }

  def step(): Unit = {
    for (creature <- creatures) {
      val action = creature.step(this)
      if(isLegal(creature, action)) {
        implementAction(creature, action)
      }
    }
  }

  def isLegal(creature: Creature, action: Action): Boolean = {
    import main.Action._
    def checkPos(dx: Int, dy: Int): Boolean = {
      val dp = Point(dx, dy)
      val checkPoint = creature.pos + dp
      space(checkPoint) match {
        case Tile.Empty => true
        case _ => false
      }
    }

    action match {
      case NoOp => true
      case Up => checkPos(0, -1)
      case Down => checkPos(0, 1)
      case Left => checkPos(-1, 0)
      case Right => checkPos(1, 0)
    }
  }

  def implementAction(creature: Creature, action: Action): Unit = {
    space(creature.pos) = Tile.Empty
    import main.Action._
    val dp: Point = action match {
      case NoOp => Point(0, 0)
      case Up => Point(0, -1)
      case Down => Point(0, 1)
      case Left => Point(-1, 0)
      case Right => Point(1, 0)
    }

    val newPos: Point = creature.pos + dp
    space(newPos) = creature.tile
    creature.pos = newPos
  }
}
