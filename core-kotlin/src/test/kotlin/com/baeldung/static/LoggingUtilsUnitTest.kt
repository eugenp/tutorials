package com.baeldung.static

import org.junit.Test

class LoggingUtilsUnitTest {
    @Test
    fun givenAPackageMethod_whenCalled_thenNoErrorIsThrown() {
        debug("test message")
    }
}