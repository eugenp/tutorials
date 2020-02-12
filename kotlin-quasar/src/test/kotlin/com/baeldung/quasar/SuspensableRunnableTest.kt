package com.baeldung.quasar

import co.paralleluniverse.fibers.Fiber
import co.paralleluniverse.fibers.Suspendable
import co.paralleluniverse.kotlin.fiber
import co.paralleluniverse.strands.SuspendableRunnable
import org.junit.Test
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException


class SuspensableRunnableTest {
    @Test
    fun createFiber() {
        class Runnable : SuspendableRunnable {
            override fun run() {
                println("Inside Fiber")
            }
        }
        val result = Fiber<Void>(Runnable()).start()
        result.join()
    }

    @Test
    fun createFiberLambda() {
        val result = Fiber<Void> {
            println("Inside Fiber Lambda")
        }
        result.start()
        result.join()
    }

    @Test
    fun createFiberDsl() {
        fiber @Suspendable {
            println("Inside Fiber DSL")
        }.join()
    }

    @Test(expected = TimeoutException::class)
    fun fiberTimeout() {
        fiber @Suspendable {
            TimeUnit.SECONDS.sleep(5)
            println("Inside Fiber DSL")
        }.join(2, TimeUnit.SECONDS)
    }
}
