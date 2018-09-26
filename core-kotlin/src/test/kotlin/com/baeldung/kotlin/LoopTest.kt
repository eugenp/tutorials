package com.baeldung.kotlin

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class LoopTest {

    @Test
    fun givenLoop_whenBreak_thenComplete() {
        var value = 0

        for (i in 1..100) {
            value = i
            if (value == 30)
                break;
        }

        assertEquals(value, 30)

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
            if (it == 3){
                // local return to the caller of the lambda, i.e. the forEach loop
                return@lit
            }
            result.add(it)
        }

        assert(1 in result
                && 2 in result
                && 4 in result
                && 5 in result)
        assertFalse(3 in result)
    }

    @Test
    fun givenLambda_whenReturnWithImplicitLabel_thenComplete() {
        var result = mutableListOf<Int>();

        listOf(1, 2, 3, 4, 5).forEach {
            if (it == 3){
                // local return to the caller of the lambda, i.e. the forEach loop
                return@forEach
            }
            result.add(it)
        }

        assert(1 in result
                && 2 in result
                && 4 in result
                && 5 in result)
        assertFalse(3 in result)
    }
}