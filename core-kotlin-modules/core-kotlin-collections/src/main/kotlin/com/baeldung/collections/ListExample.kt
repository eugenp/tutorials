package com.baeldung.collections

import kotlin.collections.List

class ListExample {
    fun createList(): List<String> {
        return listOf("one", "two", "three")
    }

    fun createMutableList(): MutableList<String> {
        return mutableListOf("Berlin", "Kolkata", "London")
    }
}