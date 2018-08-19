package com.baeldung.kotlinvsjava

import org.junit.Test
import kotlin.test.assertEquals

class CompanionObjectTest {

    @Test
    fun givenAClassWithCompanionObject_whenCallingMethodTheSameAsStaticOne_thenWeGetAResult() {
        assertEquals("A", A.returnClassName())
    }

}

class A {
    companion object {
        fun returnClassName(): String {
            return "A"
        }
    }
}