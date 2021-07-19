package com.baeldung.find

import com.baeldung.find.Person
import org.junit.Test

import static org.junit.Assert.*

class MapFindUnitTest {

    private final personMap = [
      Regina : new Person("Regina", "Fitzpatrick", 25),
      Abagail: new Person("Abagail", "Ballard", 26),
      Lucian : new Person("Lucian", "Walter", 30)
    ]

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
