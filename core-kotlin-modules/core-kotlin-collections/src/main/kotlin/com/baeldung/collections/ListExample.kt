package com.baeldung.collections

import kotlin.collections.List

class ListExample {

    private val countries = listOf("Germany", "India", "Japan", "Brazil", "Australia")
    private val cities = mutableListOf("Berlin", "Calcutta", "Seoul", "Sao Paulo", "Sydney")

    fun createList(): List<String> {
        val countryList = listOf("Germany", "India", "Japan", "Brazil")
        return countryList
    }

    fun createMutableList(): MutableList<String> {
        val cityList = mutableListOf("Berlin", "Calcutta", "Seoul", "Sao Paulo")
        return cityList
    }

    fun iterateUsingForLoop() {
        countries.forEach { it -> print("$it ") }
        println()

        for (country in countries) {
            print("$country ")
        }
        println()

        for (i in 0 until countries.size) {
            print("${countries[i]} ")
        }
        println()

        countries.forEachIndexed { i, e ->
            println("country[$i] = $e")
        }
    }

    fun iterateUsingListIterator() {
        val iterator = countries.listIterator()
        while (iterator.hasNext()) {
            val country = iterator.next()
            print("$country ")
        }
        println()

        while (iterator.hasPrevious()) {
            println("Index: ${iterator.previousIndex()}")
        }
    }

    fun iterateUsingIterator() {
        val iterator = cities.iterator()
        iterator.next()
        iterator.remove()
        println(cities)
    }

    fun iterateUsingMutableListIterator() {
        val iterator = cities.listIterator(1)
        iterator.next()
        iterator.add("London")
        iterator.next()
        iterator.set("Milan")
        println(cities)
    }

    fun retrieveElementsInList(): String {
        println(countries[2])
        return countries[2]
    }

    fun retrieveElementsUsingGet(): String {
        println(countries.get(3))
        return countries.get(3)
    }

    fun retrieveElementsFirstAndLast(): String? {
        println(countries.first())
        println(countries.last())
        println(countries.first { it.length > 7 })
        println(countries.last { it.startsWith("J") })
        println(countries.firstOrNull { it.length > 8 })
        return countries.firstOrNull { it.length > 8 }
    }

    //5. Retrieve parts of the list
}