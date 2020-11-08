package com.baeldung.ifelseexpression

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals

class IfElseExpressionExampleTest {

    @Test
    fun givenNumber_whenIfStatementCalled_thenReturnsString() {
        assertEquals("Positive number", ifStatementUsage())
    }

    @Test
    fun givenNumber_whenIfElseStatementCalled_thenReturnsString() {
        assertEquals("Negative number", ifElseStatementUsage())
    }

    @Test
    fun givenNumber_whenIfElseExpressionCalled_thenReturnsString() {
        assertEquals("Negative number", ifElseExpressionUsage())
    }

    @Test
    fun givenNumber_whenIfElseExpressionSingleLineCalled_thenReturnsString() {
        assertEquals("Negative number", ifElseExpressionSingleLineUsage())
    }

    @Test
    fun givenNumber_whenIfElseMultipleExpressionCalled_thenReturnsNumber() {
        assertEquals(73, ifElseMultipleExpressionUsage())
    }

    @Test
    fun givenNumber_whenIfElseLadderExpressionCalled_thenReturnsString() {
        assertEquals("Double digit number", ifElseLadderExpressionUsage())
    }

    @Test
    fun givenNumber_whenIfElseNestedExpressionCalled_thenReturnsNumber() {
        assertEquals(89, ifElseNestedExpressionUsage())
    }
}