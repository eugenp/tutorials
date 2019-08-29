package com.baeldung.map

import com.baeldung.Person
import org.junit.Test

import static org.junit.Assert.*

class MapUnitTest {

    private final personMap = [
      Regina : new Person("Regina", "Fitzpatrick", 25),
      Abagail: new Person("Abagail", "Ballard", 26),
      Lucian : new Person("Lucian", "Walter", 30)
    ]

    @Test
    void whenUsingEach_thenMapIsIterated() {
        def map = [
            'FF0000' : 'Red',
            '00FF00' : 'Lime',
            '0000FF' : 'Blue',
            'FFFF00' : 'Yellow'
        ]

        map.each { println "Hex Code: $it.key = Color Name: $it.value" }
    }

    @Test
    void whenUsingEachWithEntry_thenMapIsIterated() {
        def map = [
            'E6E6FA' : 'Lavender',
            'D8BFD8' : 'Thistle',
            'DDA0DD' : 'Plum',
        ]

        map.each { entry -> println "Hex Code: $entry.key = Color Name: $entry.value" }
    }

    @Test
    void whenUsingEachWithKeyAndValue_thenMapIsIterated() {
        def map = [
            '000000' : 'Black',
            'FFFFFF' : 'White',
            '808080' : 'Gray'
        ]

        map.each { key, val ->
            println "Hex Code: $key = Color Name $val"
        }
    }

    @Test
    void whenUsingEachWithIndexAndEntry_thenMapIsIterated() {
        def map = [
            '800080' : 'Purple',
            '4B0082' : 'Indigo',
            '6A5ACD' : 'Slate Blue'
        ]

        map.eachWithIndex { entry, index ->
            def indent = ((index == 0 || index % 2 == 0) ? "   " : "")
            println "$indent Hex Code: $entry.key = Color Name: $entry.value"
        }
    }

    @Test
    void whenUsingEachWithIndexAndKeyAndValue_thenMapIsIterated() {
        def map = [
            'FFA07A' : 'Light Salmon',
            'FF7F50' : 'Coral',
            'FF6347' : 'Tomato',
            'FF4500' : 'Orange Red'
        ]

        map.eachWithIndex { key, val, index ->
            def indent = ((index == 0 || index % 2 == 0) ? "   " : "")
            println "$indent Hex Code: $key = Color Name: $val"
        }
    }

    @Test
    void whenUsingForLoop_thenMapIsIterated() {
        def map = [
            '2E8B57' : 'Seagreen',
            '228B22' : 'Forest Green',
            '008000' : 'Green'
        ]

        for (entry in map) {
            println "Hex Code: $entry.key = Color Name: $entry.value"
        }
    }

    @Test
    void whenMapContainsKeyElement_thenCheckReturnsTrue() {
        def map = [a: 'd', b: 'e', c: 'f']

        assertTrue(map.containsKey('a'))
        assertFalse(map.containsKey('e'))
        assertTrue(map.containsValue('e'))
    }

    @Test
    void whenMapContainsKeyElement_thenCheckByMembershipReturnsTrue() {
        def map = [a: 'd', b: 'e', c: 'f']

        assertTrue('a' in map)
        assertFalse('f' in map)
    }

    @Test
    void whenMapContainsFalseBooleanValues_thenCheckReturnsFalse() {
        def map = [a: true, b: false, c: null]

        assertTrue(map.containsKey('b'))
        assertTrue('a' in map)
        assertFalse('b' in map)
        assertFalse('c' in map)
    }

    @Test
    void givenMapOfPerson_whenUsingStreamMatching_thenShouldEvaluateMap() {
        assertTrue(personMap.keySet().stream().anyMatch {it == "Regina"})
        assertFalse(personMap.keySet().stream().allMatch {it == "Albert"})
        assertFalse(personMap.values().stream().allMatch {it.age < 30})
        assertTrue(personMap.entrySet().stream().anyMatch {it.key == "Abagail" && it.value.lastname == "Ballard"})
    }

    @Test
    void givenMapOfPerson_whenUsingCollectionMatching_thenShouldEvaluateMap() {
        assertTrue(personMap.keySet().any {it == "Regina"})
        assertFalse(personMap.keySet().every {it == "Albert"})
        assertFalse(personMap.values().every {it.age < 30})
        assertTrue(personMap.any {firstname, person -> firstname == "Abagail" && person.lastname == "Ballard"})
    }

    @Test
    void givenMapOfPerson_whenUsingCollectionFind_thenShouldReturnElements() {
        assertNotNull(personMap.find {it.key == "Abagail" && it.value.lastname == "Ballard"})
        assertTrue(personMap.findAll {it.value.age > 20}.size() == 3)
    }

    @Test
    void givenMapOfPerson_whenUsingStreamFind_thenShouldReturnElements() {
        assertTrue(
          personMap.entrySet().stream()
            .filter {it.key == "Abagail" && it.value.lastname == "Ballard"}
            .findAny().isPresent())
        assertTrue(
          personMap.entrySet().stream()
            .filter {it.value.age > 20}
            .findAll().size() == 3)
    }
}
