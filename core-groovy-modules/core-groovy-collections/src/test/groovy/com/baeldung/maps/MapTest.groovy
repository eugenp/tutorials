package com.baeldung.maps;

import static groovy.test.GroovyAssert.*
import org.junit.Test

class MapTest{

    @Test
    void createMap() {

        def emptyMap = [:]
        assertNotNull(emptyMap)

        assertTrue(emptyMap instanceof java.util.LinkedHashMap)

        def map = [name:"Jerry", age: 42, city: "New York"]
        assertTrue(map.size() == 3)
    }

    @Test
    void addItemsToMap() {

        def map = [name:"Jerry"]

        map["age"] = 42

        map.city = "New York"

        def hobbyLiteral = "hobby"
        def hobbyMap = [(hobbyLiteral): "Singing"]
        map.putAll(hobbyMap)

        assertTrue(map == [name:"Jerry", age: 42, city: "New York", hobby:"Singing"])
        assertTrue(hobbyMap.hobby == "Singing")
        assertTrue(hobbyMap[hobbyLiteral] == "Singing")
        
        map.plus([1:20]) // returns new map

        map << [2:30]

    }

    @Test
    void getItemsFromMap() {

        def map = [name:"Jerry", age: 42, city: "New York", hobby:"Singing"]

        assertTrue(map["name"] == "Jerry")

        assertTrue(map.name == "Jerry")

        def propertyAge = "age"
        assertTrue(map[propertyAge] == 42)
    }

    @Test
    void removeItemsFromMap() {

        def map = [1:20, a:30, 2:42, 4:34, ba:67, 6:39, 7:49]

        def minusMap = map.minus([2:42, 4:34]);
        assertTrue(minusMap == [1:20, a:30, ba:67, 6:39, 7:49])

        minusMap.removeAll{it -> it.key instanceof String}
        assertTrue( minusMap == [ 1:20, 6:39, 7:49])

        minusMap.retainAll{it -> it.value %2 == 0}
        assertTrue( minusMap == [1:20])
    }

    @Test
    void iteratingOnMaps(){
        def map = [name:"Jerry", age: 42, city: "New York", hobby:"Singing"]

        map.each{ entry -> println "$entry.key: $entry.value" }

        map.eachWithIndex{ entry, i -> println "$i $entry.key: $entry.value" }

        map.eachWithIndex{ key, value, i -> println "$i $key: $value" }
    }

    @Test
    void filteringAndSearchingMaps(){
        def map = [name:"Jerry", age: 42, city: "New York", hobby:"Singing"]

        assertTrue(map.find{ it.value == "New York"}.key == "city")

        assertTrue(map.findAll{ it.value == "New York"} == [city : "New York"])

        map.grep{it.value == "New York"}.each{ it -> assertTrue(it.key == "city" && it.value == "New York")}

        assertTrue(map.every{it -> it.value instanceof String} == false)

        assertTrue(map.any{it -> it.value instanceof String} == true)
    }

    @Test
    void collect(){

        def map = [1: [name:"Jerry", age: 42, city: "New York"],
            2: [name:"Long", age: 25, city: "New York"],
            3: [name:"Dustin", age: 29, city: "New York"],
            4: [name:"Dustin", age: 34, city: "New York"]]

        def names = map.collect{entry -> entry.value.name} // returns only list
        assertTrue(names == ["Jerry", "Long", "Dustin", "Dustin"])

        def uniqueNames = map.collect([] as HashSet){entry -> entry.value.name}
        assertTrue(uniqueNames == ["Jerry", "Long", "Dustin"] as Set)

        def idNames = map.collectEntries{key, value -> [key, value.name]}
        assertTrue(idNames == [1:"Jerry", 2: "Long", 3:"Dustin", 4: "Dustin"])

        def below30Names = map.findAll{it.value.age < 30}.collect{key, value -> value.name}
        assertTrue(below30Names == ["Long", "Dustin"])


    }

    @Test
    void group(){
        def map = [1:20, 2: 40, 3: 11, 4: 93]

        def subMap = map.groupBy{it.value % 2}
        println subMap
        assertTrue(subMap == [0:[1:20, 2:40 ], 1:[3:11, 4:93]])

        def keySubMap = map.subMap([1, 2])
        assertTrue(keySubMap == [1:20, 2:40])

    }

    @Test
    void sorting(){
        def map = [ab:20, a: 40, cb: 11, ba: 93]

        def naturallyOrderedMap = map.sort()
        assertTrue([a:40, ab:20, ba:93, cb:11] == naturallyOrderedMap)

        def compSortedMap = map.sort({ k1, k2 -> k1 <=> k2 } as Comparator)
        assertTrue([a:40, ab:20, ba:93, cb:11] == compSortedMap)

        def cloSortedMap = map.sort({ it1, it2 -> it1.value <=> it1.value })
        assertTrue([cb:11, ab:20, a:40, ba:93] == cloSortedMap)

    }

}
