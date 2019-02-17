package com.baeldung.lambda

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class SplittingTest {
    private val list = listOf(0, "a", 1, "b", 2, "c");

    private val unevenList = listOf(0, "a", 1, "b", 2, "c", 3);

    private fun verifyList(resultList: List<List<Any>>) {
        assertEquals("[[0, a], [1, b], [2, c]]", resultList.toString())
    }

    private fun verifyPartialList(resultList: List<List<Any>>) {
        assertEquals("[[0, a], [1, b], [2, c], [3]]", resultList.toString())
    }

    @Test
    fun whenChunked_thenListIsSplit() {
        val resultList = list.chunked(2)
        verifyList(resultList)
    }

    @Test
    fun whenUnevenChunked_thenListIsSplit() {
        val resultList = unevenList.chunked(2)
        verifyPartialList(resultList)
    }

    @Test
    fun whenWindowed_thenListIsSplit() {
        val resultList = list.windowed(2, 2)
        verifyList(resultList)
    }

    @Test
    fun whenUnevenPartialWindowed_thenListIsSplit() {
        val resultList = unevenList.windowed(2, 2, partialWindows = true)
        verifyPartialList(resultList)
    }

    @Test
    fun whenUnevenWindowed_thenListIsSplit() {
        val resultList = unevenList.windowed(2, 2, partialWindows = false)
        verifyList(resultList)
    }

    @Test
    fun whenGroupByWithAscendingNumbers_thenListIsSplit() {
        val numberList = listOf(1, 2, 3, 4, 5, 6);
        val resultList = numberList.groupBy { (it + 1) / 2 }.values
        assertEquals("[[1, 2], [3, 4], [5, 6]]", resultList.toString())
    }

    @Test
    fun whenGroupByWithAscendingNumbersUneven_thenListIsSplit() {
        val numberList = listOf(1, 2, 3, 4, 5, 6, 7);
        val resultList = numberList.groupBy { (it + 1) / 2 }.values
        assertEquals("[[1, 2], [3, 4], [5, 6], [7]]", resultList.toString())
    }

    @Test
    fun whenGroupByWithRandomNumbers_thenListIsSplitInWrongWay() {
        val numberList = listOf(1, 3, 8, 20, 23, 30);
        val resultList = numberList.groupBy { (it + 1) / 2 }.values
        assertEquals("[[1], [3], [8], [20], [23], [30]]", resultList.toString())
    }

    @Test
    fun whenWithIndexGroupBy_thenListIsSplit() {
        val resultList = list.withIndex()
                .groupBy { it.index / 2 }
                .map { it.value.map { it.value } }
        verifyList(resultList)
    }

    @Test
    fun whenWithIndexGroupByUneven_thenListIsSplit() {
        val resultList = unevenList.withIndex()
                .groupBy { it.index / 2 }
                .map { it.value.map { it.value } }
        verifyPartialList(resultList)
    }

    @Test
    fun whenFoldIndexed_thenListIsSplit() {
        val resultList = list.foldIndexed(ArrayList<ArrayList<Any>>(list.size / 2)) { index, acc, item ->
            if (index % 2 == 0) {
                acc.add(ArrayList(2))
            }
            acc.last().add(item)
            acc
        }
        verifyList(resultList)
    }
}