package com.baeldung.scala

/**
 * Sample higher order functions.
 *
 * @author Chandra Prakash
 *
 */
object HigherOrderFunctions {

  def mapReduce(r : (Int, Int) => Int,
                i : Int,
                m : Int => Int,
                a : Int, b : Int): Int = {
    def iter(a : Int, result : Int) : Int = {
      if (a > b) result
      else iter(a + 1, r(m(a), result))
    }
    iter(a, i)
  }

  def whileLoop(condition : => Boolean)(body : => Unit) : Unit =
    if (condition) {
      body
      whileLoop(condition)(body)
    }
}