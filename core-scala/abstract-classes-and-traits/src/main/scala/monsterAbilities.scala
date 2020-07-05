import FlightMode.FlightMode

object FlightMode extends Enumeration {
  type FlightMode = Value
  val EarthBound: FlightMode.Value = Value("earthbound")
  val TakingOff: FlightMode.Value = Value("taking-off")
  val Flying: FlightMode.Value = Value("flying")
  val Landing: FlightMode.Value = Value("landing")
}

trait CanFly {
  this: Monster =>

  private var flightMode = FlightMode.EarthBound
  def getFlightMode: FlightMode = flightMode

  protected def displayMonsterFlightMode(): Unit = {
    println(s"$name is $flightMode")
  }

  def takeOff(): Unit = {
    flightMode = FlightMode.TakingOff
    displayMonsterFlightMode()
  }

  def flying(): Unit = {
    flightMode = FlightMode.Flying
    displayMonsterFlightMode()
  }

  def landing(): Unit = {
    flightMode = FlightMode.Landing
    displayMonsterFlightMode()
  }
}

trait BeserkerMode  {
  this: Monster =>

  def beserkerMode(active: Boolean): Unit = {
    if (active) {
      println(s"$name is fighting in beserker mode")
    } else {
      println(s"$name is fighting in normal mode")
    }
  }
}