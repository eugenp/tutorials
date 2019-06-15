package com.baeldung.quasar

import co.paralleluniverse.strands.dataflow.Val
import co.paralleluniverse.strands.dataflow.Var
import org.junit.Assert
import org.junit.Test
import java.util.concurrent.TimeUnit

class DataflowTest {
    @Test
    fun testValVar() {
        val a = Var<Int>()
        val b = Val<Int>()

        val c = Var<Int> { a.get() + b.get() }
        val d = Var<Int> { a.get() * b.get() }

        // (a*b) - (a+b)
        val initialResult = Val<Int> { d.get() - c.get() }
        val currentResult = Var<Int> { d.get() - c.get() }

        a.set(2)
        b.set(4)

        Assert.assertEquals(2, initialResult.get())
        Assert.assertEquals(2, currentResult.get())

        a.set(3)

        TimeUnit.SECONDS.sleep(1)

        Assert.assertEquals(2, initialResult.get())
        Assert.assertEquals(5, currentResult.get())
    }
}
