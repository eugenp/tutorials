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

    fun iterateUsingForEachLoop(): List<Int> {
        val countryLength = mutableListOf<Int>()
        countries.forEach { it ->
            print("$it ")
            println(" Length: ${it.length}")
            countryLength.add(it.length)
        }
        return countryLength
    }

    fun iterateUsingForLoop(): List<Int> {
        val countryLength = mutableListOf<Int>()
        for (country in countries) {
            print("$country ")
            println(" Length: ${country.length}")
            countryLength.add(country.length)
        }
        return countryLength
    }

    fun iterateUsingForLoopRange(): List<Int> {
        val countryLength = mutableListOf<Int>()
        for (i in 0 until countries.size) {
            print("${countries[i]} ")
            println(" Length: ${countries[i].length}")
            countryLength.add(countries[i].length)
        }
        return countryLength
    }

    fun iterateUsingForEachIndexedLoop(): List<Int> {
        val countryLength = mutableListOf<Int>()
        countries.forEachIndexed { i, e ->
            println("country[$i] = $e")
            print(" Index: $i")
            println(" Length: ${e.length}")
            countryLength.add(e.length)
        }
        return countryLength
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

    fun retrieveSubList(): List<String> {
        val subList = countries.subList(1, 4)
        println(subList)
        return subList
    }

    fun retrieveListSliceUsingIndices(): List<String> {
        val sliceList = countries.slice(1..4)
        println(sliceList)
        return sliceList
    }

    fun retrieveListSliceUsingIndicesList(): List<String> {
        val sliceList = countries.slice(listOf(1, 4))
        println(sliceList)
        return sliceList
    }

    fun countList(): Int {
        val count = countries.count()
        println(count)
        return count
    }

    fun countListUsingPredicate(): Int {
        val count = countries.count { it.length > 5 }
        println(count)
        return count
    }

    fun countListUsingProperty(): Int {
        val size = countries.size
        println(size)
        return size
    }

    fun addToList(): List<String> {
        cities.add("Barcelona")
        println(cities)
        cities.add(3, "London")
        println(cities)
        cities.addAll(listOf("Singapore", "Moscow"))
        println(cities)
        cities.addAll(2, listOf("Prague", "Amsterdam"))
        println(cities)
        return cities
    }

    fun removeFromList(): List<String> {
        cities.remove("Seoul")
        println(cities)
        cities.removeAt(1)
        println(cities)
        return cities
    }

    fun replaceFromList(): List<String> {
        cities.set(3, "Prague")
        println(cities)
        cities[4] = "Moscow"
        println(cities)
        cities.fill("Barcelona")
        println(cities)
        return cities
    }

    fun sortMutableList(): List<String> {
        cities.sort()
        println(cities)
        cities.sortDescending()
        println(cities)
        return cities
    }

    fun sortList(): List<String> {
        val sortedCountries = countries.sorted()
        println("countries = $countries")
        println("sortedCountries = $sortedCountries")
        val sortedCountriesDescending = countries.sortedDescending()
        println("countries = $countries")
        println("sortedCountriesDescending = $sortedCountriesDescending")
        return sortedCountriesDescending
    }

    fun checkOneElementInList(): Boolean {
        return countries.contains("Germany")
    }

    fun checkOneElementInListUsingOperator(): Boolean {
        return "Spain" in countries
    }

    fun checkElementsInList(): Boolean {
        return cities.containsAll(listOf("Calcutta", "Sao Paulo", "Sydney"))
    }
}