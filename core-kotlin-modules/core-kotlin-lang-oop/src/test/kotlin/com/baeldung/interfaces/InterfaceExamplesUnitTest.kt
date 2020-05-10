package com.baeldung.interfaces

import org.junit.Test
import kotlin.test.assertEquals

class InterfaceExamplesUnitTest {
    @Test
    fun givenAnInterface_whenImplemented_thenBehavesAsOverridden() {
        val simpleClass = SimpleClass()
        assertEquals("Hello, from: First Property", simpleClass.firstMethod())
        assertEquals("Hello, from: Second Property, Overridden!First Property", simpleClass.secondMethod())
    }

    @Test
    fun givenMultipleInterfaces_whenImplemented_thenBehavesAsOverridden() {
        val someClass = SomeClass()
        assertEquals("Hello, from someMethod in SomeClass", someClass.someMethod())
        assertEquals("Hello, from anotherMethod in SomeClass", someClass.anotherMethod())
    }

    @Test
    fun givenConflictingInterfaces_whenImplemented_thenBehavesAsOverridden() {
        val childClass = ChildClass()
        assertEquals("Hello, from someMethod in SecondChildInterface", childClass.someMethod())
    }

    @Test
    fun givenAnInterface_whenImplemented_thenBehavesAsDelegated() {
        val myClass = MyClass()
        assertEquals("Hello, World!", MyDerivedClass(myClass).someMethod())
    }
}