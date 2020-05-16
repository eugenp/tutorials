package com.baeldung.kovenant

import nl.komponents.kovenant.*
import nl.komponents.kovenant.Kovenant.deferred
import nl.komponents.kovenant.combine.and
import nl.komponents.kovenant.combine.combine
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.io.IOException
import java.util.*
import java.util.concurrent.TimeUnit

class KovenantTest {

    @Before
    fun setupTestMode() {
        Kovenant.testMode { error ->
            println("An unexpected error occurred")
            Assert.fail(error.message)
        }
    }

    @Test
    fun testSuccessfulDeferred() {
        val def = deferred<Long, Exception>()
        Assert.assertFalse(def.promise.isDone())

        def.resolve(1L)
        Assert.assertTrue(def.promise.isDone())
        Assert.assertTrue(def.promise.isSuccess())
        Assert.assertFalse(def.promise.isFailure())
    }

    @Test
    fun testFailedDeferred() {
        val def = deferred<Long, Exception>()
        Assert.assertFalse(def.promise.isDone())

        def.reject(RuntimeException())
        Assert.assertTrue(def.promise.isDone())
        Assert.assertFalse(def.promise.isSuccess())
        Assert.assertTrue(def.promise.isFailure())
    }

    @Test
    fun testResolveDeferredTwice() {
        val def = deferred<Long, Exception>()
        def.resolve(1L)
        try {
            def.resolve(1L)
        } catch (e: AssertionError) {
            // Expected.
            // This is slightly unusual. The AssertionError comes from Assert.fail() from setupTestMode()
        }
    }

    @Test
    fun testSuccessfulTask() {
        val promise = task { 1L }
        Assert.assertTrue(promise.isDone())
        Assert.assertTrue(promise.isSuccess())
        Assert.assertFalse(promise.isFailure())
    }

    @Test
    fun testFailedTask() {
        val promise = task { throw RuntimeException() }
        Assert.assertTrue(promise.isDone())
        Assert.assertFalse(promise.isSuccess())
        Assert.assertTrue(promise.isFailure())
    }

    @Test
    fun testCallbacks() {
        val promise = task { 1L }

        promise.success {
            println("This was a success")
            Assert.assertEquals(1L, it)
        }

        promise.fail {
            println(it)
            Assert.fail("This shouldn't happen")
        }

        promise.always {
            println("This always happens")
        }
    }

    @Test
    fun testGetValues() {
        val promise = task { 1L }
        Assert.assertEquals(1L, promise.get())
    }

    @Test
    fun testAllSucceed() {
        val numbers = all(
                task { 1L },
                task { 2L },
                task { 3L }
        )

        Assert.assertEquals(listOf(1L, 2L, 3L), numbers.get())
    }

    @Test
    fun testOneFails() {
        val runtimeException = RuntimeException()

        val numbers = all(
                task { 1L },
                task { 2L },
                task { throw runtimeException }
        )

        Assert.assertEquals(runtimeException, numbers.getError())
    }

    @Test
    fun testAnySucceeds() {
        val promise = any(
                task {
                    TimeUnit.SECONDS.sleep(3)
                    1L
                },
                task {
                    TimeUnit.SECONDS.sleep(2)
                    2L
                },
                task {
                    TimeUnit.SECONDS.sleep(1)
                    3L
                }
        )

        Assert.assertTrue(promise.isDone())
        Assert.assertTrue(promise.isSuccess())
        Assert.assertFalse(promise.isFailure())
    }

    @Test
    fun testAllFails() {
        val runtimeException = RuntimeException()
        val ioException = IOException()
        val illegalStateException = IllegalStateException()
        val promise = any(
                task {
                    TimeUnit.SECONDS.sleep(3)
                    throw runtimeException
                },
                task {
                    TimeUnit.SECONDS.sleep(2)
                    throw ioException
                },
                task {
                    TimeUnit.SECONDS.sleep(1)
                    throw illegalStateException
                }
        )

        Assert.assertTrue(promise.isDone())
        Assert.assertFalse(promise.isSuccess())
        Assert.assertTrue(promise.isFailure())
    }

    @Test
    fun testSimpleCombine() {
        val promise = task { 1L } and task { "Hello" }
        val result = promise.get()

        Assert.assertEquals(1L, result.first)
        Assert.assertEquals("Hello", result.second)
    }

    @Test
    fun testLongerCombine() {
        val promise = combine(
                task { 1L },
                task { "Hello" },
                task { Currency.getInstance("USD") }
        )
        val result = promise.get()

        Assert.assertEquals(1L, result.first)
        Assert.assertEquals("Hello", result.second)
        Assert.assertEquals(Currency.getInstance("USD"), result.third)
    }
}
