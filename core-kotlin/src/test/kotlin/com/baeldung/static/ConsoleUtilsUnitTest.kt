package com.baeldung.static

import org.junit.Test

class ConsoleUtilsUnitTest {
    @Test
    fun givenAStaticMethod_whenCalled_thenNoErrorIsThrown() {
        ConsoleUtils.debug("test message")
    }
}