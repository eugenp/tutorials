package com.baeldung.filter

import org.apache.commons.math3.primes.Primes
import org.junit.jupiter.api.Assertions.assertIterableEquals
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

internal class FilterTest {

    @Test
    fun givenAscendingValueMap_whenFilteringOnValue_ThenReturnSubsetOfMap() {
        val originalMap = mapOf("key1" to 1, "key2" to 2, "key3" to 3)
        val filteredMap = originalMap.filter { it.value < 2 }
        val expectedMap = mapOf("key1" to 1)

        assertTrue { expectedMap == filteredMap }
    }

    @Test
    fun givenSeveralCollections_whenFilteringToAccumulativeList_thenListContainsAllContents() {
        val array1 = arrayOf(90, 92, 93, 94, 92, 95, 93)
        val array2 = sequenceOf(51, 31, 83, 674_506_111, 256_203_161, 15_485_863)
        val list1 = listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
        val primes = mutableListOf<Int>()

        val expected = listOf(2, 3, 5, 7, 31, 83, 15_485_863, 256_203_161, 674_506_111)

        val primeCheck = { num: Int -> Primes.isPrime(num) }

        array1.filterTo(primes, primeCheck)
        list1.filterTo(primes, primeCheck)
        array2.filterTo(primes, primeCheck)

        primes.sort()

        assertIterableEquals(expected, primes)
    }

}