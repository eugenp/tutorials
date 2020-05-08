package com.baeldung.core_scala.inheritance.abstract_classes

abstract class Machine(owner: String) {
  val weight: Int
}
 
class Computer(owner: String) extends Machine(owner) {
  val weight: Int = 3
}
