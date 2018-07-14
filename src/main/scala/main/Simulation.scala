package main

import common.Profiling

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits._


class Simulation(world: World) {
  var curStep = 0

  def start (render: => Unit): Future[Unit] = Future {
    while(true) {
      curStep += 1
      //println(s"curStep = $curStep")
      val (stepTime, _) =
        Profiling.time {
          step()
        }
      val delayFrame = 30 //ms
      val delay =
        if(stepTime > delayFrame) None
        else Some(delayFrame - stepTime)
      sleep(delay)
      render
    }
  } recover {
    case exception: Throwable =>
      exception.printStackTrace()
      //println(s"exception: ${exception}")
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
