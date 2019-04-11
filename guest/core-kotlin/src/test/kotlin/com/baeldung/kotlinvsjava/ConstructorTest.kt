package com.baeldung.kotlinvsjava

import org.junit.Test
import kotlin.test.assertEquals

class ConstructorTests {

    @Test
    fun givenAClassWithPrimaryConstructor_whenCreatingAnInstance_thenWeGetObject() {
        var example = Example(1, "Example")

        assertEquals(1, example.id)
        assertEquals("Example", example.name)
    }

}

class Example constructor(val id: Int, var name: String)