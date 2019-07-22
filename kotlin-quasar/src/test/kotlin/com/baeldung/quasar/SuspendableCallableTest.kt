package com.baeldung.quasar

import co.paralleluniverse.fibers.Fiber
import co.paralleluniverse.fibers.Suspendable
import co.paralleluniverse.kotlin.fiber
import co.paralleluniverse.strands.SuspendableCallable
import org.junit.Assert
import org.junit.Test
import java.util.concurrent.TimeUnit


class SuspendableCallableTest {
    @Test
    fun createFiber() {
        class Callable : SuspendableCallable<String> {
            override fun run(): String {
                println("Inside Fiber")
                return "Hello"
            }
        }
        val result = Fiber<String>(Callable()).start()

        Assert.assertEquals("Hello", result.get())
    }

    @Test
    fun createFiberLambda() {
        val lambda: (() -> String) = {
            println("Inside Fiber Lambda")
            "Hello"
        }
        val result = Fiber<String>(lambda)
        result.start()

        Assert.assertEquals("Hello", result.get())
    }

    @Test
    fun createFiberDsl() {
        val result = fiber @Suspendable {
            TimeUnit.SECONDS.sleep(5)
            println("Inside Fiber DSL")
            "Hello"
        }

        Assert.assertEquals("Hello", result.get())
    }
}
