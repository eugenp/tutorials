package com.baeldung.examples

object PatternMatchingExample extends App {

  abstract class Item
  case class Book(title:String, author:String, price:Double) extends Item
  case class MusicCD(title:String, genre:String, price:Double) extends Item

  def describe(item:Item) = item match {

    case b:Book if b.price > 1000 => println(s"The book ${b.title} is expensive")

    case b:Book => println(s"The book ${b.title} is written by ${b.author}.")

    case MusicCD(title, "Jazz", _) => println(s"This is a CD of Jazz music.")

    case _ => println("Unknown Item")

  }

  describe(Book("Half Girlfriend", "Chetan Bhagat", 100))
}
