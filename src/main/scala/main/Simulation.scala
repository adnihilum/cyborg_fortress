package main

import common.Profiling
import scala.util.{Failure, Try}


class Simulation(world: World) {
  var curStep = 0

  def start (render: => Unit): Thread = {
    def loop(): Unit = {
      Try {
        while (true) {
          curStep += 1
          //println(s"curStep = $curStep")
          val (stepTime, _) =
            Profiling.time {
              step()
            }
          val fpsLock: Double = 30
          val delayFrame = ((1.0 / fpsLock) * 1000.0).toInt //ms
          val delay =
            if (stepTime > delayFrame) None
            else Some(delayFrame - stepTime)
          sleep(delay)
          render
        }
      } match {
        case Failure(exception)=>
          exception.printStackTrace()
        case _ =>
      }
    }
    val thread = new Thread{loop()}
    thread.start()
    thread
  }

  def sleep(delayOpt: Option[Int]): Unit = delayOpt match {
    case None =>
    case Some(delay) =>
      Thread.sleep(delay)
  }

  def step(): Unit = {
    world.step()
  }
}
