package com.baeldung.lambda

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class LambdaTest {

    @Test
    fun whenCallingALambda_thenPerformTheAction() {
        assertEquals(9, inferredType(3))
    }

    @Test
    fun whenCallingAMoreComplicatedLambda_thenPerformTheAction() {
        assertEquals("500", intToBiggerString(5))
    }

    @Test
    fun whenPassingALambdaObject_thenCallTriggerLambda() {
        val lambda = { arg: Double ->
            arg == 4.329
        }

        val result = invokeLambda(lambda)

        assertTrue(result)
    }

    @Test
    fun whenPassingALambdaLiteral_thenCallTriggerLambda() {
        val result = invokeLambda({
            true
        })

        assertTrue(result)
    }

    @Test
    fun whenPassingALambdaLiteralOutsideBrackets_thenCallTriggerLambda() {
        val result = invokeLambda { arg -> arg.isNaN() }

        assertFalse(result)
    }

    @Test
    fun whenPassingAnAnonymousFunction_thenCallTriggerLambda() {
        val result = invokeLambda(fun(arg: Double): Boolean {
            return arg >= 0
        })

        assertTrue(result)
    }

    @Test
    fun whenUsingLambda_thenCalculateGrade() {
        val gradeCalculation = getCalculationLambda()

        assertEquals(false, gradeCalculation(-40))
        assertEquals("Pass", gradeCalculation(50))
    }

    @Test
    fun whenUsingReturnStatementLambda_thenCalculateGrade() {
        val gradeCalculation: Int.() -> String = getCalculationLambdaWithReturn()

        assertEquals("Distinction", 80.gradeCalculation())
        assertEquals("Error", 244_234_324.gradeCalculation())
    }

    @Test
    fun whenUsingAnonymousFunction_thenCalculateGrade() {
        val gradeCalculation = getCalculationAnonymousFunction()

        assertEquals("Error", gradeCalculation(244_234_324))
        assertEquals("Pass", gradeCalculation(50))
    }

    @Test
    fun whenPassingAFunctionReference_thenCallTriggerLambda() {
        val reference = Double::isFinite
        val result = invokeLambda(reference)

        assertTrue(result)
    }

    @Test
    fun givenArray_whenMappingArray_thenPerformCalculationOnAllElements() {
        val expected = listOf("100", "200", "300", "400", "500")
        val actual = manyLambda(arrayOf(1, 2, 3, 4, 5))

        assertEquals(expected, actual)
    }

}