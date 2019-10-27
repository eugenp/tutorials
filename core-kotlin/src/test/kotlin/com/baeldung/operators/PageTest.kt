package com.baeldung.operators

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class PageTest {

    private val page = PageImpl(1, 10, "Java", "Kotlin", "Scala")

    @Test
    fun `Get convention should work as expected`() {
        assertEquals(page[1], "Kotlin")
        assertEquals(page[1, 3], listOf("Kotlin", "Scala"))
    }

    @Test
    fun `In convention should work on a page as expected`() {
        assertTrue("Kotlin" in page)
    }

}

private class PageImpl<T>(val page: Int, val size: Int, vararg val elements: T) : Page<T> {
    override fun pageNumber(): Int = page
    override fun pageSize(): Int = size
    override fun elements(): MutableList<T> = mutableListOf(*elements)
}