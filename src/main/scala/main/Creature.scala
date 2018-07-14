package main

import common.Point
import path_finder.{Cell, Path, WalkSpace}
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

    world.space(p) match {
      case Tile.Empty => p
      case _ => getRandomGoal
    }
  }

  def canWalk(curPos: Point): (Point, Tile) => Boolean = {
    (pos, t) =>
      t match {
        case Tile.Empty => true
        case _ if curPos == pos => true
        case _ => false
      }
  }

  def canWalkTerrain: (Point, Tile) => Boolean = {
    (_, t) =>
      t match {
        case Tile.Empty | _: Tile.Creature => true
        case _ => false
      }
  }



  def step: Action = {
    //println("creature step")
    if(pos == goal){
      goal = getRandomGoal
    }

    val walkSpace = WalkSpace.fromOtherSpace(world.space, canWalk(pos))

    val walkSpaceTerrain = WalkSpace.fromOtherSpace(world.space, canWalkTerrain)

    import common.Profiling._
    //println(s"pos==goal => ${pos == goal} ")
    val pathOpt = Path.findP(walkSpace, pos, goal)

    val pathTerrainOpt = Path.findP(walkSpaceTerrain, pos, goal)

    (pathOpt, pathTerrainOpt) match {
      case (None, None) =>
        goal = getRandomGoal
        //println("case1")
        Action.NoOp

      case (None, Some(_)) =>
        //println("case2")
        Action.NoOp

      case (Some(path), _) =>
        val dp = path.points(1) - pos
        //println(s"pos: $pos\npaths first points: ${path.points.slice(0, 3)}")
        //println(s"path: ${path.show}")

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

    }
    //    Action.NoOp
  }
}
