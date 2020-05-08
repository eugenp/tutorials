package com.baeldung.core_scala.inheritance

// trait Machine(owner: String) {
//   val weight: Int
// } // raises compile error: traits or objects may not have parameters
 
// class Computer(owner: String) extends Machine(owner) {
//   val weight: Int = 3
// }

trait Machine {
  val owner: String
  val weight: Int
}
 
class Computer(val owner: String) extends Machine {
  val weight: Int = 3
}