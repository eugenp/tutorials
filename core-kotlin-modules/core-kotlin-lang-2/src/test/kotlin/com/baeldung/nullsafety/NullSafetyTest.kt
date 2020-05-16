package com.baeldung.nullsafety

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull
import kotlin.test.assertTrue


class NullSafetyTest {

    @Test
    fun givenNonNullableField_whenAssignValueToIt_thenNotNeedToCheckAgainstNull() {
        //given
        var a: String = "value"
        //a = null compilation error

        //then
        assertEquals(a.length, 5)
    }

    @Test
    fun givenNullableField_whenReadValue_thenNeedToCheckAgainstNull() {
        //given
        var b: String? = "value"
        b = null

        //when
        if (b != null) {

        } else {
            assertNull(b)
        }
    }

    @Test
    fun givenComplexObject_whenUseSafeCall_thenShouldChainCallsResultingWithValue() {
        //given
        val p: Person? = Person(Country("ENG"))

        //when
        val res = p?.country?.code

        //then
        assertEquals(res, "ENG")
    }

    @Test
    fun givenComplexObject_whenUseSafeCall_thenShouldChainCallsResultingWithNull() {
        //given
        val p: Person? = Person(Country(null))

        //when
        val res = p?.country?.code

        //then
        assertNull(res)
    }

    @Test
    fun givenCollectionOfObjects_whenUseLetOperator_thenShouldApplyActionOnlyOnNonNullValue() {
        //given
        val firstName = "Tom"
        val secondName = "Michael"
        val names: List<String?> = listOf(firstName, null, secondName)

        //when
        var res = listOf<String?>()
        for (item in names) {
            item?.let { res = res.plus(it); it }
                    ?.also{it -> println("non nullable value: $it")}
        }

        //then
        assertEquals(2, res.size)
        assertTrue { res.contains(firstName) }
        assertTrue { res.contains(secondName) }
    }

    @Test
    fun fivenCollectionOfObject_whenUseRunOperator_thenExecuteActionOnNonNullValue(){
        //given
        val firstName = "Tom"
        val secondName = "Michael"
        val names: List<String?> = listOf(firstName, null, secondName)

        //when
        var res = listOf<String?>()
        for (item in names) {
            item?.run{res = res.plus(this)}
        }

        //then
        assertEquals(2, res.size)
        assertTrue { res.contains(firstName) }
        assertTrue { res.contains(secondName) }
    }

    @Test
    fun givenNullableReference_whenUseElvisOperator_thenShouldReturnValueIfReferenceIsNotNull() {
        //given
        val value: String? = "name"

        //when
        val res = value?.length ?: -1

        //then
        assertEquals(res, 4)
    }

    @Test
    fun givenNullableReference_whenUseElvisOperator_thenShouldReturnDefaultValueIfReferenceIsNull() {
        //given
        val value: String? = null

        //when
        val res = value?.length ?: -1

        //then
        assertEquals(res, -1)
    }

    @Test
    fun givenNullableField_whenUsingDoubleExclamationMarkOperatorOnNull_thenThrowNPE() {
        //given
        var b: String? = "value"
        b = null

        //when
        assertFailsWith<NullPointerException> {
            b!!.length
        }
    }

    @Test
    fun givenNullableField_whenUsingDoubleExclamationMarkOperatorOnNotNull_thenReturnValue() {
        //given
        val b: String? = "value"

        //then
        assertEquals(b!!.length, 5)
    }

    @Test
    fun givenNullableList_whenUseFilterNotNullMethod_thenRemoveALlNullValues() {
        //given
        val list: List<String?> = listOf("a", null, "b")

        //when
        val res = list.filterNotNull()

        //then
        assertEquals(res.size, 2)
        assertTrue { res.contains("a") }
        assertTrue { res.contains("b") }
    }
}

data class Person(val country: Country?)

data class Country(val code: String?)