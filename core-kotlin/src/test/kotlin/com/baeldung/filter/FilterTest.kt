package com.baeldung.filter

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

}