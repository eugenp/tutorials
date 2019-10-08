package com.baeldung.kotlin.objects

import org.junit.Assert
import org.junit.Test

class ObjectsTest {
    @Test
    fun singleton() {

        Assert.assertEquals(42, SimpleSingleton.answer)
        Assert.assertEquals("Hello, world!", SimpleSingleton.greet("world"))
    }

    @Test
    fun counter() {
        Assert.assertEquals(0, Counter.currentCount())
        Counter.increment()
        Assert.assertEquals(1, Counter.currentCount())
        Counter.decrement()
        Assert.assertEquals(0, Counter.currentCount())
    }

    @Test
    fun comparator() {
        val strings = listOf("Hello", "World")
        val sortedStrings = strings.sortedWith(ReverseStringComparator)

        Assert.assertEquals(listOf("World", "Hello"), sortedStrings)
    }

    @Test
    fun companion() {
        Assert.assertEquals("You can see me", OuterClass.public)
        // Assert.assertEquals("You can't see me", OuterClass.secret) // Cannot access 'secret'
    }
}
