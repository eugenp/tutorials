package com.baeldung.combine.arrays

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class MergeArraysUnitTest {

    val arr1 = arrayOf(3, 10, 2)
    val arr2 = arrayOf(6, 11, 89, 0)

    val expectedMergedArray = arrayOf(3, 10, 2, 6, 11, 89, 0)

    lateinit var mergeArrays: MergeArrays

    @BeforeEach
    fun setUp() {
        mergeArrays = MergeArrays()
    }


    @Test
    fun givenTwoArrays_whenCombinePlusIsCalled_ArraysAreMerged() {
        assertEqualArrays(expectedMergedArray, mergeArrays.combinePlus(arr1, arr2))
    }

    @Test
    fun givenTwoArrays_whenCombinePlusOperatorIsCalled_ArraysAreMerged() {
        assertEqualArrays(expectedMergedArray, mergeArrays.combinePlusOperator(arr1, arr2))
    }

    @Test
    fun givenTwoArrays_whenCombineSpreadIsCalled_ArraysAreMerged() {
        assertEqualArrays(expectedMergedArray, mergeArrays.combineSpread(arr1, arr2))
    }

    @Test
    fun givenTwoArrays_whenCombineCustomIsCalled_ArraysAreMerged() {
        assertEqualArrays(expectedMergedArray, mergeArrays.combineCustom(arr1, arr2))
    }

    private fun assertEqualArrays(arr1: Array<Int>, arr2: Array<Int>) {
        assertEquals(arr1.contentToString(), arr2.contentToString())
    }
}


