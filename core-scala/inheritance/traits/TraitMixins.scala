package com.baeldung.core_scala.inheritance

trait Heavy {
  def isHeavy(): Boolean = true
}

trait Light {
  def isHeavy(): Boolean = false
}

trait Precise {
  def precision: Int
}

trait Operable {
  def operate(): Unit
}

class Computer extends Operable with Heavy with Precise {
  val precision: Int = 3
  def operate(): Unit = print("Booting up...")
}

class WashingMachine extends Operable with Light {
  def operate(): Unit = print("Spinning...")
}

object TraitMixins {
  def main(args: Array[String]): Unit = {
    val c: Computer = new Computer
    c.isHeavy() // true
    c.operate() // Booting up...

    val wm: WashingMachine = new WashingMachine
    c.isHeavy() // false
    wm.operate() // Spinning...
  }
}
