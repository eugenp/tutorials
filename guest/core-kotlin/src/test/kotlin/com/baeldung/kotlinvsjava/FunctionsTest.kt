package com.baeldung.kotlinvsjava

import org.junit.Test
import kotlin.test.assertEquals

class FunctionsTest {

    @Test
    fun givenALambdaExpressionConcatenatingString_whenUsingTheFunctionWithAAndBString_thenWeGetAB() {
        val concat: (String, String) -> String = { a, b -> a + b }

        assertEquals("AB", concat("A","B"))
    }

    @Test
    fun givenAnAnonymousFunctionConcatenatingString_whenUsingTheFunctionWithAAndBString_thenWeGetAB() {
        val concat: (String, String) -> String = fun(a: String, b: String): String { return a + b }

        assertEquals("AB", concat("A","B"))
    }

    @Test
    fun givenAnPlusMethodOfString_whenUsingTheFunctionWithAAndBString_thenWeGetAB() {
        val concat = String::plus

        assertEquals("AB", concat("A","B"))
    }

    @Test
    fun givenAStringConstractorAssignedToFunction_whenUsingFunctionReference_thenWeGetNewString() {
        val concat = ::String

        assertEquals("A", concat().plus("A"))
    }

    @Test
    fun givenAClassImplementingAFunctionType_whenUsingTheFunctionWithAAndBString_thenWeGetAB() {
        val concat = StringConcatenation()

        assertEquals("AB", concat("A", "B"))
    }

    @Test
    fun givenALambdaExpressionWithReceiver_whenUsingTheFunctionWithReceiver_thenWeGetABC() {
        val concat: String.(String, String) -> String = { a, b -> plus(a).plus(b) }

        assertEquals("ABC", "A".concat("B", "C"))
    }

    @Test
    fun givenALambdaExpressionWithinLambdaExpression_whenUsingTheFunction_thenWeGetAB() {
        val concat: (String) -> ((String) -> String) = { a -> {b -> a + b} }

        assertEquals("AB", (concat("A")("B")))
    }

    @Test
    fun given3NestedLambdaExpression_whenUsingTheFunction_thenWeGetABC() {
        val concat: (String) -> (String) -> (String) -> String = { a -> {b -> { c -> a + b + c} } }

        assertEquals("ABC", concat("A")("B")("C"))
    }

}

class StringConcatenation: (String, String) -> String {
    override fun invoke(p1: String, p2: String): String {
        return p1 + p2
    }
}