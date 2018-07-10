package main

import common.Point
import path_finder.{Path, WalkSpace}
import cats._
import cats.implicits._
import path_finder.ShowImplicits._

import scala.util.Random

trait Action
object Action {
  case object Up extends Action
  case object Down extends Action
  case object Right extends Action
  case object Left extends Action
  case object NoOp extends Action
}

class Creature(var pos: Point, val tile: Tile)(implicit world: World) {
  val rnd = new Random

  var goal: Point = getRandomGoal

  def getRandomGoal: Point = {
    val p = Point(
      rnd.nextInt(world.space.dim.width),
      rnd.nextInt(world.space.dim.height)
    )
    if(canWalk(world.space(p))) p
    else getRandomGoal
  }

  def canWalk(t: Tile): Boolean = t match {
    case Tile.Empty | _:Tile.Creature => true
    case _ => false
  }

  def step: Action = {
    if(pos == goal){//(pos.dist(goal) < 10) {
      goal = getRandomGoal
    }

    val walkSpace = WalkSpace.fromOtherSpace(world.space, canWalk)

    try {
      val path = Path.find(walkSpace, pos, goal)
      val dp = path.points(1) - pos
      //println(s"pos: $pos\npaths first points: ${path.points.slice(0, 3)}")
      println(s"path: ${path.show}}")

      val action =
        dp match {
          case Point(0, 0) => {
            goal = getRandomGoal
            println("zero")
            Action.NoOp
          }
          case Point(-1, 0) =>
            Action.Left
          case Point(1, 0) =>
            Action.Right
          case Point(0, -1) =>
            Action.Up
          case Point(0, 1) =>
            Action.Down
          case p =>
            println(s"something wrong! $p")
            Action.NoOp
        }
      //println(s"action = $action")
      action
    } catch {
      case e: Exception if e.getMessage == "there is no path" =>
        println(s"exception: $e")
        goal = getRandomGoal
        Action.NoOp
    }
//    Action.NoOp
  }
}
