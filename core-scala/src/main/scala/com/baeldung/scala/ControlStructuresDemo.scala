package com.baeldung.scala

/**
 * Sample code demonstrating the various control structured.
 *
 * @author Chandra Prakash
 *
 */
object ControlStructuresDemo {
  def gcd(x : Int, y : Int) : Int = {
    if (y == 0) x else gcd(y, x % y)
  }

  def gcdIter(x : Int, y : Int) : Int = {
    var a = x
    var b = y
    while (b > 0) {
      a = a % b
      val t = a
      a = b
      b = t
    }
    a
  }

  def rangeSum(a : Int, b : Int) = {
    var sum = 0;
    for (i <- a to b) {
      sum += i
    }
    sum
  }

  def factorial(a : Int) : Int = {
    var result = 1;
    var i = 1;
    do {
      result *= i
      i = i + 1
    } while (i <= a)
    result
  }

}