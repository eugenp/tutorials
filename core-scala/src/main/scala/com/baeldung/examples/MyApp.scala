package com.baeldung.examples

/**
 * @author ${user.name}
 */
object MyApp {
  
  def foo(x : Array[String]) = x.foldLeft("")((a,b) => a + b)
  
  def main(args : Array[String]) {
    println( "Hello World!" )
    println("Number of arguments passed:" + args.length)
  }

}
