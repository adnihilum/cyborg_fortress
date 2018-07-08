package main

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits._


class Simulation(world: World) {
  def start (render: => Unit): Future[Unit] = Future {
    while(true) {
      sleep()
      step()
      render
    }
  }

  def sleep(): Unit = {
    Thread.sleep(100)
  }

  def step(): Unit = {
    world.step()
  }
}
