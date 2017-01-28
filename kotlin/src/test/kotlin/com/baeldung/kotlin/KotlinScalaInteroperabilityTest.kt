package com.baeldung.kotlin

import com.baeldung.java.StringUtils
import org.junit.Test
import kotlin.test.assertEquals


class KotlinScalaInteroperabilityTest {
    @Test
    fun givenLowercaseString_whenExecuteMethodFromJavaStringUtils_shouldReturnStringUppercase() {
        //given
        val name = "tom"

        //when
        val res = StringUtils.toUpperCase(name)

        //then
        assertEquals(res, "TOM")
    }
}
