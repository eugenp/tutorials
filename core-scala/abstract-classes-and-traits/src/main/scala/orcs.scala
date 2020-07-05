class Orc(name: String) extends Monster(name, 50) {
  override def move(): Unit = println(s"Orc $name moved 1 space (${getClass.getSimpleName})")
}

class UrukHai(name: String) extends Monster(name, 100) with BeserkerMode {
  override def move(): Unit = println(s"Uruk-hai $name moved 2 spaces (${getClass.getSimpleName})")
}
