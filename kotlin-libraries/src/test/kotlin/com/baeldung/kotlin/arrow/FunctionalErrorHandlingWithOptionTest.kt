package com.baeldung.kotlin.arrow

import org.junit.Assert
import org.junit.Test

class FunctionalErrorHandlingWithOptionTest {

    val operator = FunctionalErrorHandlingWithOption()

    @Test
    fun givenInvalidInput_thenErrorMessageIsPresent(){
        val useComputeOption = operator.computeWithOptionClient("foo")
        Assert.assertEquals("Not an even number!", useComputeOption)
    }

    @Test
    fun givenOddNumberInput_thenErrorMessageIsPresent(){
        val useComputeOption = operator.computeWithOptionClient("539")
        Assert.assertEquals("Not an even number!",useComputeOption)
    }

    @Test
    fun givenEvenNumberInputWithNonSquareNum_thenFalseMessageIsPresent(){
        val useComputeOption = operator.computeWithOptionClient("100")
        Assert.assertEquals("The greatest divisor is square number: false",useComputeOption)
    }

    @Test
    fun givenEvenNumberInputWithSquareNum_thenTrueMessageIsPresent(){
        val useComputeOption = operator.computeWithOptionClient("242")
        Assert.assertEquals("The greatest divisor is square number: true",useComputeOption)
    }

}