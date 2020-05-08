package com.baeldung.core_scala.inheritance.abstract_classes

abstract class Machine(owner: String) {
  val weight: Int
  def isHeavy(): Boolean = weight >= 5
  def operate(): Unit
}
 
class Computer(owner: String) extends Machine(owner) {
  val weight: Int = 3
  def operate(): Unit = print("Booting up...")
}
 
class WashingMachine(owner: String) extends Machine(owner) {
  val weight: Int = 10
  def operate(): Unit = print("Spinning...")
}
 
object AbstractClasses {
  def main(args: Array[String]): Unit = {
    val c: Machine = new Computer(owner = "John")
    c.isHeavy() // false
    
    val wm: Machine = new WashingMachine(owner = "Sarah")
    wm.isHeavy // true
    
    c.operate() // Booting up...
  }
}
