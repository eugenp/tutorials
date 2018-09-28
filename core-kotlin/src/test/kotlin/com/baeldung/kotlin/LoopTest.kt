package com.baeldung.kotlin

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class LoopTest {

    @Test
    fun givenLoop_whenBreak_thenComplete() {
        var value = 0

        //break loop without label
        for (i in 1..10) {
            value = i
            if (value == 3)
                break;
        }

        assertEquals(value, 3)

        //break loop with label
        outer_loop@ for (i in 1..10) {
            for (j in 1..10) {
                value = i * j
                if (value == 30)
                    break@outer_loop;
            }
        }

        assertEquals(value, 30)
    }

    @Test
    fun givenLoop_whenContinue_thenComplete() {
        var processedList = mutableListOf<Int>()
        //continue loop without label
        for (i in 1..10) {
            if (i == 3)
                continue;
            processedList.add(i)
        }

        assert(processedList.all { it -> it != 3 })

        //continue loop with label
        processedList = mutableListOf<Int>()
        outer_loop@ for (i in 1..10) {
            for (j in 1..10) {
                if (i == 3)
                    continue@outer_loop;
                processedList.add(i*j)
            }
        }

        assertEquals(processedList.size, 90)
    }

    @Test
    fun givenLambda_whenReturn_thenComplete() {
        listOf(1, 2, 3, 4, 5).forEach {
            if (it == 3) return // non-local return directly to the caller
            assert(it < 3)
        }
        //this point is unreachable
        assert(false);
    }

    @Test
    fun givenLambda_whenReturnWithExplicitLabel_thenComplete() {
        var result = mutableListOf<Int>();

        listOf(1, 2, 3, 4, 5).forEach lit@{
            if (it == 3) {
                // local return to the caller of the lambda, i.e. the forEach loop
                return@lit
            }
            result.add(it)
        }

        assert(result.all { it -> it != 3 });
    }

    @Test
    fun givenLambda_whenReturnWithImplicitLabel_thenComplete() {
        var result = mutableListOf<Int>();

        listOf(1, 2, 3, 4, 5).forEach {
            if (it == 3) {
                // local return to the caller of the lambda, i.e. the forEach loop
                return@forEach
            }
            result.add(it)
        }

        assert(result.all { it -> it != 3 });
    }

    @Test
    fun givenAnonymousFunction_return_thenComplete() {
        var result = mutableListOf<Int>();
        listOf(1, 2, 3, 4, 5).forEach(fun(element: Int) {
            if (element == 3) return  // local return to the caller of the anonymous fun, i.e. the forEach loop
            result.add(element);
        })

        assert(result.all { it -> it != 3 });
    }

    @Test
    fun givenAnonymousFunction_returnToLabel_thenComplete() {
        var value = 0;
        run loop@{
            listOf(1, 2, 3, 4, 5).forEach {
                value = it;
                if (it == 3) return@loop // non-local return from the lambda passed to run
            }
        }
        assertEquals(value, 3)
    }
}