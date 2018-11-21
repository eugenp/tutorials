package com.baeldung.scala

/**
 * An abstract class for set of integers and its implementation.
 *
 * @author Chandra Prakash
 *
 */
abstract class IntSet {
  // add an element to the set
  def incl(x : Int) : IntSet

  // whether an element belongs to the set
  def contains(x : Int) : Boolean
}

class EmptyIntSet extends IntSet {

  def contains(x : Int) : Boolean = false

  def incl(x : Int) =
    new NonEmptyIntSet(x, this)
}

class NonEmptyIntSet(val head : Int, val tail : IntSet)
  extends IntSet {

  def contains(x : Int) : Boolean =
    head == x || (tail contains x)

  def incl(x : Int) : IntSet =
    if (this contains x) this
    else new NonEmptyIntSet(x, this)
}