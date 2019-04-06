package com.baeldung.kotlin

import com.baeldung.kotlin.ListExtension
import org.junit.Test
import kotlin.test.assertTrue

class ListExtensionTest {
    @Test
    fun givenList_whenExecuteExtensionFunctionOnList_shouldReturnRandomElementOfList(){
        //given
        val elements = listOf("a", "b", "c")

        //when
        val result = ListExtension().getRandomElementOfList(elements)

        //then
        assertTrue(elements.contains(result))
    }
}