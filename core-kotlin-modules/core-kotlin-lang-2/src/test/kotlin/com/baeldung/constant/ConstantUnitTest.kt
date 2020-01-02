package com.baeldung.constant

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ConstantUnitTest {

    @Test
    fun givenConstant_whenCompareWithActualValue_thenReturnTrue() {
        assertEquals(10, TestKotlinConstantObject.COMPILE_TIME_CONST)
        assertEquals(30, TestKotlinConstantObject.RUN_TIME_CONST)
        assertEquals(20, TestKotlinConstantObject.JAVA_STATIC_FINAL_FIELD)

        assertEquals(40, TestKotlinConstantClass.COMPANION_OBJECT_NUMBER)
    }
}

