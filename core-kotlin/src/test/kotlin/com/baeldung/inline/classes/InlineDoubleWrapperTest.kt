package com.baeldung.inline.classes

import org.junit.Test
import kotlin.test.assertEquals

class InlineDoubleWrapperTest {

    @Test
    fun whenInclineClassIsUsed_ThenPropertyIsReadCorrectly() {
        val piDoubleValue = InlineDoubleWrapper(3.14)
        assertEquals(3.14, piDoubleValue.doubleValue)
    }
}