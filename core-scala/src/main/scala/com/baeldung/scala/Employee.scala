package com.baeldung.scala

/**
 * Sample Code demonstrating a class.
 *
 * @author Chandra Prakash
 *
 */
class Employee(val name : String,
               var salary : Int,
               annualIncrement : Int = 20) {

  def incrementSalary() : Unit = {
    salary += annualIncrement
  }

  override def toString =
    s"Employee(name=$name, salary=$salary)"
}

/**
 * A Trait which will make the toString return upper case value.
 */
trait UpperCasePrinter {
  override def toString: String = super.toString toUpperCase
}

