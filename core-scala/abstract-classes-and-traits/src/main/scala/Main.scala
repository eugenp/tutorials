object Main extends App {
  val monsters: List[Monster] = List[Monster](
    new Orc("Skin & Bones"),
    new UrukHai("Crusher"),
    new Dragon("Fledgling", "Brown"),
    new FlyingDragon("Smaug"),
    new Orc("Surprise!") with CanFly,
    new Orc("Baddie") with CanFly with BeserkerMode
  )

  println("** Making flying monsters take off...")
  monsters.filter(_.isInstanceOf[CanFly]).map(_.asInstanceOf[CanFly]).foreach(_.takeOff())

  println("** Moving all the monsters 1 round")
  monsters.foreach(_.move())

  println("** Making flying monsters fly...")
  monsters.filter(_.isInstanceOf[CanFly]).map(_.asInstanceOf[CanFly]).foreach(_.flying())

  println("** Moving all the monsters 1 round")
  monsters.foreach(_.move())

  println("** Going beserk!")
  monsters.filter(_.isInstanceOf[BeserkerMode]).map(_.asInstanceOf[BeserkerMode]).foreach(_.beserkerMode(true))
}