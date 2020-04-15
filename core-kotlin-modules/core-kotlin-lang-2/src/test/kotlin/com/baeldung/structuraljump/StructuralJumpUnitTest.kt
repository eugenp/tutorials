package com.baeldung.structuraljump

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class StructuralJumpUnitTest {

    @Test
    fun givenLoop_whenBreak_thenComplete() {
        var value = ""
        for (i in "hello_world") {
            if (i == '_')
                break
            value += i.toString()
        }
        assertEquals("hello", value)
    }
    @Test
    fun givenLoop_whenBreakWithLabel_thenComplete() {
        var value = ""
        outer_loop@ for (i in 'a'..'d') {
            for (j in 1..3) {
                value += "" + i + j
                if (i == 'b' && j == 1)
                    break@outer_loop
            }
        }
        assertEquals("a1a2a3b1", value)
    }

    @Test
    fun givenLoop_whenContinue_thenComplete() {
        var result = ""
        for (i in "hello_world") {
            if (i == '_')
                continue
            result += i
        }
        assertEquals("helloworld", result)
    }
    @Test
    fun givenLoop_whenContinueWithLabel_thenComplete() {
        var result = ""
        outer_loop@ for (i in 'a'..'c') {
            for (j in 1..3) {
                if (i == 'b')
                    continue@outer_loop
                result += "" + i + j
            }
        }
        assertEquals("a1a2a3c1c2c3", result)
    }

    @Test
    fun givenLambda_whenReturn_thenComplete() {
        var result = returnInLambda();
        assertEquals("hello", result)
    }

    private fun returnInLambda(): String {
        var result = ""
        "hello_world".forEach {
            // non-local return directly to the caller
            if (it == '_') return result
            result += it.toString()
        }
        //this line won't be reached
        return result;
    }

    @Test
    fun givenLambda_whenReturnWithExplicitLabel_thenComplete() {
        var result = ""
        "hello_world".forEach lit@{
            if (it == '_') {
                // local return to the caller of the lambda, i.e. the forEach loop
                return@lit
            }
            result += it.toString()
        }
        assertEquals("helloworld", result)
    }

    @Test
    fun givenLambda_whenReturnWithImplicitLabel_thenComplete() {
        var result = ""
        "hello_world".forEach {
            if (it == '_') {
                // local return to the caller of the lambda, i.e. the forEach loop
                return@forEach
            }
            result += it.toString()
        }
        assertEquals("helloworld", result)
    }

    @Test
    fun givenAnonymousFunction_return_thenComplete() {
        var result = ""
        "hello_world".forEach(fun(element) {
            // local return to the caller of the anonymous fun, i.e. the forEach loop
            if (element == '_') return
            result += element.toString()
        })
        assertEquals("helloworld", result)
    }

    @Test
    fun givenAnonymousFunction_returnToLabel_thenComplete() {
        var result = ""
        run loop@{
            "hello_world".forEach {
                // non-local return from the lambda passed to run
                if (it == '_') return@loop
                result += it.toString()
            }
        }
        assertEquals("hello", result)
    }
}
