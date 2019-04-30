package com.baeldung.kotlin

import org.junit.Test
import kotlin.test.assertEquals

class ArrayInitializationTest {

    @Test
    fun givenArrayOfStrings_thenValuesPopulated() {
        val strings = arrayOf("January", "February", "March")

        assertEquals(3, strings.size)
        assertEquals("March", strings[2])
    }

    @Test
    fun givenArrayOfIntegers_thenValuesPopulated() {
        val integers = intArrayOf(1, 2, 3, 4)

        assertEquals(4, integers.size)
        assertEquals(1, integers[0])
    }

    @Test
    fun givenArrayOfNulls_whenPopulated_thenValuesPresent() {
        val array = arrayOfNulls<Number>(5)

        for (i in array.indices) {
            array[i] = i * i
        }

        assertEquals(16, array[4])
    }

    @Test
    fun whenGeneratorUsed_thenValuesPresent() {
        val generatedArray = IntArray(10) { i -> i * i }

        assertEquals(81, generatedArray[9])
    }

    @Test
    fun whenStringGenerated_thenValuesPresent() {
        val generatedStringArray = Array(10) { i -> "Number of index: $i"  }

        assertEquals("Number of index: 0", generatedStringArray[0])
    }

}