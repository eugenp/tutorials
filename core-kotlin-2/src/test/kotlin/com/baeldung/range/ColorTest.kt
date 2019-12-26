package com.baeldung.range

import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ColorTest {

    @Test
    fun testEnumRange() {

        println(Color.values().toList());
        val red = Color.RED
        val yellow = Color.YELLOW
        val range = red..yellow

        assertTrue { range.contains(Color.MAGENTA) }
        assertFalse { range.contains(Color.BLUE) }
    }
}