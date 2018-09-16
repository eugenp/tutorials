package main.main.com.baeldung.kotlin.arrays

import com.baeldung.kotlin.arrays.arraysWithArrayOf
import com.baeldung.kotlin.arrays.arraysWithArrayOfNull
import com.baeldung.kotlin.arrays.arraysWithFunctionToValues
import org.junit.Test

class ArraysKotlinKtTest {

    @Test
    fun creatingArraysWithFunction() {
        val arrays = arraysWithFunctionToValues(5)
        assert(arrays.size == 5)
    }

    @Test
    fun creatingArraysWithArrayOf() {
        val arrays = arraysWithArrayOf(1,3,5)
        assert(arrays.size == 3)
        assert(arrays.contains(1))
        assert(arrays.contains(3))
        assert(arrays.contains(5))
    }

    @Test
    fun creatingArraysOfNull() {
        val arrays = arraysWithArrayOfNull(10)
        assert (arrays.size == 10)
        assert (arrays.all { it == null })
    }
}