package com.baeldung.kotlin.arrow

import arrow.core.None
import arrow.core.Option
import arrow.core.Some
import kotlin.math.sqrt

class FunctionalErrorHandlingWithOption {

    fun parseInput(s : String) : Option<Int> = Option.fromNullable(s.toIntOrNull())

    fun isEven(x : Int) : Boolean = x % 2 == 0

    fun biggestDivisor(x: Int) : Int = biggestDivisor(x, 2)

    fun biggestDivisor(x : Int, y : Int) : Int {
        if(x == y){
            return 1;
        }
        if(x % y == 0){
            return x / y;
        }
        return biggestDivisor(x, y+1)
    }

    fun isSquareNumber(x : Int) : Boolean {
        val sqrt: Double = sqrt(x.toDouble())
        return sqrt % 1.0 == 0.0
    }

    fun computeWithOption(input : String) : Option<Boolean> {
        return parseInput(input)
                .filter(::isEven)
                .map(::biggestDivisor)
                .map(::isSquareNumber)
    }

    fun computeWithOptionClient(input : String) : String{
        val computeOption = computeWithOption(input)

        return when(computeOption){
            is None -> "Not an even number!"
            is Some -> "The greatest divisor is square number: ${computeOption.t}"
        }
    }
}