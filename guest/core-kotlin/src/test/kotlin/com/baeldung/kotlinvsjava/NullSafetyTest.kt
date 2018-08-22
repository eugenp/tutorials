package com.baeldung.kotlinvsjava

import kotlin.test.Test
import java.lang.NullPointerException
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class NullSafetyTest {

    @Test
    fun givenStringAndNull_whenUsingSafeCallOperatorWithLengthMethod_thenReturnsLengthForStringAndNullForNull() {
        val stringValue: String? = "string"
        val nullValue: String? = null

        assertNotNull(stringValue?.length)
        assertNull(nullValue?.length)
    }

    @Test(expected = NullPointerException::class)
    fun givenNullReference_whenUsingTheNotNullAssertionOperator_thenItThrowsNullPointerException() {
        val stringValue: String? = "string"
        val nullValue: String? = null

        assertNotNull(stringValue!!.length)
        nullValue!!.length
    }

    @Test
    fun givenStringAndNull_whenUsingElvisOperator_thenItTestsAgainstNullAndReturnsTheProperValue() {
        val stringValue: String? = "string"
        val nullValue: String? = null

        val shouldBeLength: Int = stringValue?.length ?: -1
        val souldBeMinusOne: Int = nullValue?.length ?: -1

        assertEquals(6, shouldBeLength)
        assertEquals(-1, souldBeMinusOne)
    }

    @Test
    fun givenString_whenCastingToInt_thenItReturnsNull() {
        val stringValue: String? = "string"

        val intValue: Int? = stringValue as? Int

        assertNull(intValue)
    }

    @Test
    fun givenCollectionWithNulls_whenFilterNonNull_thenItReturnsCollectionWithoutNulls() {
        val list: List<String?> = listOf("a", "b", null)
        val nonNullList = list.filterNotNull()

        assertEquals(2, nonNullList.size)
        assertEquals(nonNullList, listOf("a", "b"))
    }

    @Test
    fun givenCollectionWithNulls_whenLetWithSafeCallOperator_thenItOmitsNulls() {
        val list: List<String?> = listOf("a", "b", null)
        for(elem in list) {
            elem?.let { assertNotNull(it) }
        }
    }

}