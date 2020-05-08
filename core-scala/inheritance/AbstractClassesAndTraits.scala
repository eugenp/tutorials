package com.baeldung.core_scala.inheritance

abstract class Machine(owner: String) {
  def weight: Int
}
 
trait Precise {
  def precision: Int
}
 
trait Electronic
 
class Computer(val owner: String) extends Machine(owner) with Precise with Electronic {
  val weight: Int = 3
  val precision: Int = 5
}