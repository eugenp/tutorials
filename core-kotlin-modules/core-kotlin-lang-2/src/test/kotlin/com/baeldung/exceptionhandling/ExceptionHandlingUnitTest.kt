package com.baeldung.exceptionhandling

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.io.IOException
import kotlin.test.assertNull

class ExceptionHandlingUnitTest {

    private val classUnderTest: ExceptionHandling = ExceptionHandling()

    @Test
    fun givenInvalidConversion_whenTryCatchUsed_thenReturnsCatchBlockValue(){
        assertNull(classUnderTest.tryCatchBlock())
    }

    @Test
    fun givenInvalidConversion_whenTryCatchExpressionUsed_thenReturnsCatchBlockValue(){
        assertNull(classUnderTest.tryCatchExpression())
    }

    @Test
    fun givenDivisionByZero_whenMultipleCatchUsed_thenReturnsCatchBlockValue(){
        assertNull(classUnderTest.multipleCatchBlock())
    }

    @Test
    fun givenDivisionByZero_whenNestedTryCatchUsed_thenReturnsNestedCatchBlockValue(){
        assertNull(classUnderTest.nestedTryCatchBlock())
    }

    @Test
    fun givenInvalidConversion_whenTryCatchFinallyUsed_thenReturnsCatchAndFinallyBlock(){
        assertNull(classUnderTest.finallyBlock())
    }

    @Test
    fun givenIllegalArgument_whenThrowKeywordUsed_thenThrowsException(){
        assertThrows<IllegalArgumentException> { classUnderTest.throwKeyword() }
    }

    @Test
    fun whenAnnotationUsed_thenThrowsException(){
        assertThrows<IOException> { classUnderTest.throwsAnnotation() }
    }
}
