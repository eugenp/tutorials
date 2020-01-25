package com.baeldung.kovenant

import nl.komponents.kovenant.Promise
import nl.komponents.kovenant.any
import nl.komponents.kovenant.task
import org.junit.Assert
import org.junit.Ignore
import org.junit.Test

@Ignore
// Note that this can not run in the same test run if KovenantTest has already been executed
class KovenantTimeoutTest {
    @Test
    fun testTimeout() {
        val promise = timedTask(1000) { "Hello" }
        val result = promise.get()
        Assert.assertEquals("Hello", result)
    }

    @Test
    fun testTimeoutExpired() {
        val promise = timedTask(1000) {
            Thread.sleep(3000)
            "Hello"
        }
        val result = promise.get()
        Assert.assertNull(result)
    }

    fun <T> timedTask(millis: Long, body: () -> T) : Promise<T?, List<Exception>> {
        val timeoutTask = task {
            Thread.sleep(millis)
            null
        }
        val activeTask = task(body = body)
        return any(activeTask, timeoutTask)
    }
}
