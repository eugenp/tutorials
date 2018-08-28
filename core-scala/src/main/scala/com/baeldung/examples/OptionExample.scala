package com.baeldung.examples

object OptionExample extends App {

    def minimumEvenNumber(data:Array[Int]) = {
        val evens = data.filter(_%2 == 0)
        if (evens.isEmpty) None else Some(evens.min)
    }

    val minEven = minimumEvenNumber(Array(21, 3, 11, 7)).getOrElse(0)
    println(minEven)
}
