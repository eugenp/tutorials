package com.baeldung.maps

import spock.lang.Specification

class MapTest extends Specification {

    def "createMap"() {
        when:
        def emptyMap = [:]
        def map = [name: "Jerry", age: 42, city: "New York"]

        then:
        emptyMap != null
        emptyMap instanceof java.util.LinkedHashMap
        map.size() == 3
    }

    def "addItemsToMap"() {
        given:
        def map = [name: "Jerry"]
        def hobbyLiteral = "hobby"
        def hobbyMap = [(hobbyLiteral): "Singing"]
        def appendToMap = [:]

        when:
        map["age"] = 42
        map.city = "New York"

        map.putAll(hobbyMap)

        appendToMap.plus([1: 20])
        appendToMap << [2: 30]

        then:
        map == [name: "Jerry", age: 42, city: "New York", hobby: "Singing"]

        hobbyMap.hobby == "Singing"
        hobbyMap[hobbyLiteral] == "Singing"

        appendToMap == [2: 30] // plus(Map) returns new instance of Map
    }

    def "getItemsFromMap"() {
        when:
        def map = [name: "Jerry", age: 42, city: "New York", hobby: "Singing"]
        def propertyAge = "age"

        then:
        map["name"] == "Jerry"
        map.name == "Jerry"
        map[propertyAge] == 42
        map."$propertyAge" == 42
    }

    def "removeItemsFromMap"() {
        given:
        def map = [1: 20, a: 30, 2: 42, 4: 34, ba: 67, 6: 39, 7: 49]
        def removeAllKeysOfTypeString = [1: 20, a: 30, ba: 67, 6: 39, 7: 49]
        def retainAllEntriesWhereValueIsEven = [1: 20, a: 30, ba: 67, 6: 39, 7: 49]

        when:
        def minusMap = map - [2: 42, 4: 34]
        removeAllKeysOfTypeString.removeAll { it.key instanceof String }
        retainAllEntriesWhereValueIsEven.retainAll { it.value % 2 == 0 }

        then:
        minusMap == [1: 20, a: 30, ba: 67, 6: 39, 7: 49]
        removeAllKeysOfTypeString == [1: 20, 6: 39, 7: 49]
        retainAllEntriesWhereValueIsEven == [1: 20, a: 30]
    }

    def "iteratingOnMaps"() {
        when:
        def map = [name: "Jerry", age: 42, city: "New York", hobby: "Singing"]

        then:
        map.each { entry -> println "$entry.key: $entry.value" }
        map.eachWithIndex { entry, i -> println "$i $entry.key: $entry.value" }
        map.eachWithIndex { key, value, i -> println "$i $key: $value" }
    }

    def "filteringAndSearchingMaps"() {
        given:
        def map = [name: "Jerry", age: 42, city: "New York", hobby: "Singing"]

        when:
        def find = map.find { it.value == "New York" }
        def finaAll = map.findAll { it.value == "New York" }
        def grep = map.grep { it.value == "New York" }
        def every = map.every { it -> it.value instanceof String }
        def any = map.any { it -> it.value instanceof String }

        then:
        find.key == "city"

        finaAll instanceof Map
        finaAll == [city: "New York"]

        grep instanceof Collection
        grep.each { it -> assert it.key == "city" && it.value == "New York" }

        every instanceof Boolean
        !every

        any instanceof Boolean
        any
    }

    def "collect"() {
        given:
        def map = [
                1: [name: "Jerry", age: 42, city: "New York"],
                2: [name: "Long", age: 25, city: "New York"],
                3: [name: "Dustin", age: 29, city: "New York"],
                4: [name: "Dustin", age: 34, city: "New York"]
        ]

        when:
        def names = map.collect { entry -> entry.value.name } // returns only list
        def uniqueNames = map.collect { entry -> entry.value.name }
                             .unique()
        def idNames = map.collectEntries { key, value -> [key, value.name] }
        def below30Names = map.findAll { it.value.age < 30 }
                              .collect { key, value -> value.name }

        then:
        names == ["Jerry", "Long", "Dustin", "Dustin"]
        uniqueNames == ["Jerry", "Long", "Dustin"]
        idNames == [1: "Jerry", 2: "Long", 3: "Dustin", 4: "Dustin"]
        below30Names == ["Long", "Dustin"]
    }

    def "group"() {
        given:
        def map = [1: 20, 2: 40, 3: 11, 4: 93]

        when:
        def subMap = map.groupBy { it.value % 2 }
        def keySubMap = map.subMap([1, 2])

        then:
        subMap == [0: [1: 20, 2: 40], 1: [3: 11, 4: 93]]
        keySubMap == [1: 20, 2: 40]
    }

    def "sorting"() {
        given:
        def map = [ab: 20, a: 40, cb: 11, ba: 93]

        when:
        def naturallyOrderedMap = map.sort()
        def compSortedMap = map.sort({ k1, k2 -> k1 <=> k2 } as Comparator)
        def cloSortedMap = map.sort({ it1, it2 -> it1.value <=> it1.value })

        then:
        naturallyOrderedMap == [a: 40, ab: 20, ba: 93, cb: 11]
        compSortedMap == [a: 40, ab: 20, ba: 93, cb: 11]
        cloSortedMap == [cb: 11, ab: 20, a: 40, ba: 93]
    }
}
