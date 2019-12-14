package com.baeldung.scala

// Case Class
abstract class Animal

case class Mammal(name: String, fromSea: Boolean) extends Animal

case class Bird(name: String) extends Animal

case class Fish(name: String) extends Animal

// Sealed Class
sealed abstract class CardSuit

case class Spike() extends CardSuit

case class Diamond() extends CardSuit

case class Heart() extends CardSuit

case class Club() extends CardSuit

object Person {
  def apply(fullName: String) = fullName

  def unapply(fullName: String): Option[String] = {
    if (!fullName.isEmpty)
      Some(fullName.replaceAll("(?<=\\w)(\\w+)", "."))
    else
      None
  }
}

class PatternMatching {

  def caseClassesPatternMatching(animal: Animal): String = {
    animal match {
      case Mammal(name, fromSea) => s"I'm a $name, a kind of mammal. Am I from the sea? $fromSea"
      case Bird(name) => s"I'm a $name, a kind of bird"
      case _ => "I'm an unknown animal"
    }
  }

  def constantsPatternMatching(constant: Any): String = {
    constant match {
      case 0 => "I'm equal to zero"
      case 4.5d => "I'm a double"
      case false => "I'm the contrary of true"
      case _ => s"I'm unknown and equal to $constant"
    }
  }

  def sequencesPatternMatching(sequence: Any): String = {
    sequence match {
      case List(singleElement) => s"I'm a list with one element: $singleElement"
      case List(_, _*) => s"I'm a list with one or multiple elements: $sequence"
      case Vector(1, 2, _*) => s"I'm a vector: $sequence"
      case _ => s"I'm an unrecognized sequence. My value: $sequence"
    }
  }

  def tuplesPatternMatching(tuple: Any): String = {
    tuple match {
      case (first, second) => s"I'm a tuple with two elements: $first & $second"
      case (first, second, third) => s"I'm a tuple with three elements: $first & $second & $third"
      case _ => s"Unrecognized pattern. My value: $tuple"
    }
  }

  def typedPatternMatching(any: Any): String = {
    any match {
      case string: String => s"I'm a string. My value: $string"
      case integer: Int => s"I'm an integer. My value: $integer"
      case _ => s"I'm from an unknown type. My value: $any"
    }
  }

  def regexPatterns(toMatch: String): String = {
    val numeric = """([0-9]+)""".r
    val alphabetic = """([a-zA-Z]+)""".r
    val alphanumeric = """([a-zA-Z0-9]+)""".r

    toMatch match {
      case numeric(value) => s"I'm a numeric with value $value"
      case alphabetic(value) => s"I'm an alphabetic with value $value"
      case alphanumeric(value) => s"I'm an alphanumeric with value $value"
      case _ => s"I contain other characters than alphanumerics. My value $toMatch"
    }
  }

  def optionsPatternMatching(option: Option[String]): String = {
    option match {
      case Some(value) => s"I'm not an empty option. Value $value"
      case None => "I'm an empty option"
    }
  }

  def patternGuards(toMatch: Any, maxLength: Int): String = {
    toMatch match {
      case list: List[Any] if (list.size <= maxLength) => "List is of acceptable size"
      case list: List[Any] => "List has not an acceptable size"
      case string: String if (string.length <= maxLength) => "String is of acceptable size"
      case string: String => "String has not an acceptable size"
      case _ => "Input is neither a List or a String"
    }
  }

  def sealedClass(cardSuit: CardSuit): String = {
    cardSuit match {
      case Spike() => "Card is spike"
      case Club() => "Card is club"
      case Heart() => "Card is heart"
      case Diamond() => "Card is diamond"
    }
  }

  def extractors(person: Any): String = {
    person match {
      case Person(initials) => s"My initials are $initials"
      case _ => "Could not extract initials"
    }
  }

  def closuresPatternMatching(list: List[Any]): List[Any] = {
    list.collect { case i: Int if (i < 10) => i }
  }

  def catchBlocksPatternMatching(exception: Exception): String = {
    try {
      throw exception
    } catch {
      case ex: IllegalArgumentException => "It's an IllegalArgumentException"
      case ex: RuntimeException => "It's a RuntimeException"
      case _ => "It's an unknown kind of exception"
    }
  }
}