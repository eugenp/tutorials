def returnDifferentLists(f: Boolean) = {
  if (f) {
    List(1, 2, 3)
  } else {
    List("a", "b", "c")
  }
}

returnDifferentLists(false) match {
  case l: List[Int] =>
    println("I'm a list of integers!")
  case l: List[String] =>
    println("I'm a list of strings!")
  case _ =>
    println("Huh?")
}
