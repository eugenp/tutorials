class Dragon(name: String, val color: String) extends Monster(name, 500) {
  override def move(): Unit = {
    println(s"$color dragon $name moved 1 space (${getClass.getSimpleName})")
  }
}

class FlyingDragon(name: String) extends Dragon(name, "Red") with CanFly {
  override def move(): Unit = {
    displayMonsterFlightMode()
    getFlightMode match {
      case FlightMode.EarthBound =>
       super.move()
      case FlightMode.TakingOff | FlightMode.Landing =>
        // We don't change horizontal position if we're taking off or flying
      case FlightMode.Flying =>
        println(s"$color dragon $name moved 10 spaces")
    }
  }
}