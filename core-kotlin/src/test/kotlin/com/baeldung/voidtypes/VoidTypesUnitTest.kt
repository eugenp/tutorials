package com.baeldung.voidtypes

import org.junit.jupiter.api.Test
import kotlin.test.assertNull
import kotlin.test.assertTrue

class VoidTypesUnitTest {

    fun returnTypeAsVoid(): Void? {
        println("Function can have Void as return type")
        return null
    }

    fun unitReturnTypeForNonMeaningfulReturns(): Unit {
        println("No meaningful return")
    }

    fun unitReturnTypeIsImplicit() {
        println("Unit Return type is implicit")
    }

    fun alwaysThrowException(): Nothing {
        throw IllegalArgumentException()
    }

    fun invokeANothingOnlyFunction() {
        alwaysThrowException()

        var name = "Tom"
    }

    @Test
    fun givenJavaVoidFunction_thenMappedToKotlinUnit() {
        assertTrue(System.out.println() is Unit)
    }

    @Test
    fun givenVoidReturnType_thenReturnsNullOnly() {
        assertNull(returnTypeAsVoid())
    }

    @Test
    fun givenUnitReturnTypeDeclared_thenReturnsOfTypeUnit() {
        assertTrue(unitReturnTypeForNonMeaningfulReturns() is Unit)
    }

    @Test
    fun givenUnitReturnTypeNotDeclared_thenReturnsOfTypeUnit() {
        assertTrue(unitReturnTypeIsImplicit() is Unit)
    }
}