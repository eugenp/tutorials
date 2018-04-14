package com.baeldung.filter

import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

internal class TakeTest {

    @Test
    fun `given array of alternating types, when predecating on 'is String', then produce list of array up until predicate is false`() {
        val originalMap = arrayOf("key1", 1, "key2", 2, "key3", 3)
        val filteredMap = originalMap.takeWhile { it is String }
        val expectedMap = listOf("key1")

        println(originalMap.take(4))
        assertTrue { expectedMap == filteredMap }
    }

}