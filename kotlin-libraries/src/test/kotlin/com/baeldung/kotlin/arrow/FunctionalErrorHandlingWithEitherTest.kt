package com.baeldung.kotlin.arrow

import arrow.core.Either
import com.baeldung.kotlin.arrow.FunctionalErrorHandlingWithEither.ComputeProblem.NotANumber
import com.baeldung.kotlin.arrow.FunctionalErrorHandlingWithEither.ComputeProblem.OddNumber
import org.junit.Assert
import org.junit.Test

class FunctionalErrorHandlingWithEitherTest {

    val operator = FunctionalErrorHandlingWithEither()

    @Test
    fun givenInvalidInput_whenComputeInvoked_NotANumberIsPresent(){
        val computeWithEither = operator.computeWithEither("bar")

        Assert.assertTrue(computeWithEither.isLeft())
        when(computeWithEither){
            is Either.Left -> when(computeWithEither.a){
                NotANumber -> "Ok."
                else -> Assert.fail()
            }
            else -> Assert.fail()
        }
    }

    @Test
    fun givenOddNumberInput_whenComputeInvoked_OddNumberIsPresent(){
        val computeWithEither = operator.computeWithEither("121")

        Assert.assertTrue(computeWithEither.isLeft())
        when(computeWithEither){
            is Either.Left -> when(computeWithEither.a){
                OddNumber -> "Ok."
                else -> Assert.fail()
            }
            else -> Assert.fail()
        }
    }

    @Test
    fun givenEvenNumberWithoutSquare_whenComputeInvoked_OddNumberIsPresent(){
        val computeWithEither = operator.computeWithEither("100")

        Assert.assertTrue(computeWithEither.isRight())
        when(computeWithEither){
            is Either.Right -> when(computeWithEither.b){
                false -> "Ok."
                else -> Assert.fail()
            }
            else -> Assert.fail()
        }
    }

    @Test
    fun givenEvenNumberWithSquare_whenComputeInvoked_OddNumberIsPresent(){
        val computeWithEither = operator.computeWithEither("98")

        Assert.assertTrue(computeWithEither.isRight())
        when(computeWithEither){
            is Either.Right -> when(computeWithEither.b){
                true -> "Ok."
                else -> Assert.fail()
            }
            else -> Assert.fail()
        }
    }
}