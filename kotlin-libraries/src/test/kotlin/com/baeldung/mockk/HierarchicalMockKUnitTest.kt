package com.baeldung.mockk

import io.mockk.*
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Foo {
    lateinit var name: String
    lateinit var bar: Bar
}

class Bar {
    lateinit var nickname: String
}

class HierarchicalMockKUnitTest {

    @Test
    fun givenHierarchicalClass_whenMockingIt_thenReturnProperValue() {
        // given
        val foo = mockk<Foo> {
            every { name } returns "Karol"
            every { bar } returns mockk {
                every { nickname } returns "Tomato"
            }
        }
        // when
        val result = foo.bar.nickname
        // then
        assertEquals("Tomato", result)
    }

}