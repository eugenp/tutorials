package com.baeldung.quasar

import co.paralleluniverse.fibers.Fiber
import co.paralleluniverse.fibers.FiberAsync
import co.paralleluniverse.fibers.Suspendable
import co.paralleluniverse.kotlin.fiber
import co.paralleluniverse.strands.Strand
import org.junit.Assert
import org.junit.Test
import java.math.BigDecimal
import java.util.concurrent.TimeUnit

interface PiCallback {
    fun success(result: BigDecimal)
    fun failure(error: Exception)
}

fun computePi(callback: PiCallback) {
    println("Starting calculations")
    TimeUnit.SECONDS.sleep(2)
    println("Finished calculations")
    callback.success(BigDecimal("3.14"))
}

class PiAsync : PiCallback, FiberAsync<BigDecimal, Exception>() {
    override fun success(result: BigDecimal) {
        asyncCompleted(result)
    }

    override fun failure(error: Exception) {
        asyncFailed(error)
    }

    override fun requestAsync() {
        computePi(this)
    }
}

class PiAsyncTest {
    @Test
    fun testPi() {
        val result = fiber @Suspendable {
            val pi = PiAsync()
            println("Waiting to get PI on: " + Fiber.currentFiber().name)
            val result = pi.run()
            println("Got PI")

            result
        }.get()

        Assert.assertEquals(BigDecimal("3.14"), result)
    }
}
