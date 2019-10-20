package com.baeldung.scala

/**
 * Some utility methods.
 *
 * @author Chandra Prakash
 *
 */
object Utils {
  def average(x : Double, y : Double): Double = (x + y) / 2

  def randomLessThan(d : Double): Double = {
    var random = 0d
    do {
      random = Math.random()
    } while (random >= d)
    random
  }

  def power(x : Int, y : Int) : Int = {
    def powNested(i : Int, accumulator : Int) : Int = {
      if (i <= 0) accumulator
      else powNested(i - 1, x * accumulator)
    }
    powNested(y, 1)
  }

  def fibonacci(n : Int) : Int = n match {
    case 0 | 1 => 1
    case x if x > 1 =>
      fibonacci(x - 1) + fibonacci(x - 2)
  }
}