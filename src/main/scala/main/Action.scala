package main

trait Action

object Action extends Enumeration {
  val Up, Down, Right, Left, NoOp = Value
}