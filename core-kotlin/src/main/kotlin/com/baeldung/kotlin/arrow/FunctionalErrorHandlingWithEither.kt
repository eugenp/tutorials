package com.baeldung.kotlin.arrow

import arrow.core.Either
import arrow.core.Right
import arrow.core.filterOrElse
import kotlin.math.sqrt

class FunctionalErrorHandlingWithEither {

    sealed class ComputeException {
        object OddNumber : ComputeException()
        object NotANumber : ComputeException()
    }

    fun parseInput(s : String) : Either<ComputeException, Int> = Either.cond(s.toIntOrNull() != null, {-> s.toInt()}, {->ComputeException.NotANumber} )

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

    fun computeWithEither(input : String) : Either<ComputeException, Boolean> {
        return parseInput(input)
                .filterOrElse(::isEven) {->ComputeException.OddNumber}
                .map (::biggestDivisor)
                .map (::isSquareNumber)
    }

    fun computeWithEitherClient(input : String) {
        val computeWithEither = computeWithEither(input)

        when(computeWithEither){
            is Either.Right -> "The greatest divisor is square number: ${computeWithEither.b}"
            is Either.Left -> when(computeWithEither.a){
                is ComputeException.NotANumber -> "Wrong input! Not a number!"
                is ComputeException.OddNumber -> "It is an odd number!"
            }
        }
    }

}