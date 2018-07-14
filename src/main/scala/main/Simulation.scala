package main

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits._


class Simulation(world: World) {
  var curStep = 0

  def start (render: => Unit): Future[Unit] = Future {
    while(true) {
      curStep += 1
      //println(s"curStep = $curStep")
      sleep()
      step()
      render
    }
  } recover {
    case exception: Throwable =>
      exception.printStackTrace()
      //println(s"exception: ${exception}")
  }

  def sleep(): Unit = {
    Thread.sleep(100)
  }

  def step(): Unit = {
    world.step()
  }
}
