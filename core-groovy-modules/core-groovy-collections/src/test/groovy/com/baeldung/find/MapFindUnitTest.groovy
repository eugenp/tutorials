package com.baeldung.find

import spock.lang.Specification

class MapFindUnitTest extends Specification {

    final personMap = [
            Regina: new Person("Regina", "Fitzpatrick", 25),
            Abagail: new Person("Abagail", "Ballard", 26),
            Lucian: new Person("Lucian", "Walter", 30)
    ]

    def "whenMapContainsKeyElement_thenCheckReturnsTrue"() {
        given:
        def map = [a: 'd', b: 'e', c: 'f']

        expect:
        map.containsKey('a')
        !map.containsKey('e')
        map.containsValue('e')
    }

    def "whenMapContainsKeyElement_thenCheckByMembershipReturnsTrue"() {
        given:
        def map = [a: 'd', b: 'e', c: 'f']

        expect:
        'a' in map
        'f' !in map
    }

    def "whenMapContainsFalseBooleanValues_thenCheckReturnsFalse"() {
        given:
        def map = [a: true, b: false, c: null]

        expect:
        map.containsKey('b')
        'a' in map
        'b' !in map // get value of key 'b' and does the assertion
        'c' !in map
    }

    def "givenMapOfPerson_whenUsingStreamMatching_thenShouldEvaluateMap"() {
        expect:
        personMap.keySet().stream()
                 .anyMatch { it == "Regina" }
        !personMap.keySet().stream()
                  .allMatch { it == "Albert" }
        !personMap.values().stream()
                  .allMatch { it.age < 30 }
        personMap.entrySet().stream()
                 .anyMatch { it.key == "Abagail" && it.value.lastname == "Ballard" }
    }

    def "givenMapOfPerson_whenUsingCollectionMatching_thenShouldEvaluateMap"() {
        expect:
        personMap.keySet().any { it == "Regina" }
        !personMap.keySet().every { it == "Albert" }
        !personMap.values().every { it.age < 30 }
        personMap.any { firstname, person -> firstname == "Abagail" && person.lastname == "Ballard" }
    }

    def "givenMapOfPerson_whenUsingCollectionFind_thenShouldReturnElements"() {
        expect:
        personMap.find { it.key == "Abagail" && it.value.lastname == "Ballard" }
        personMap.findAll { it.value.age > 20 }.size() == 3
    }

    def "givenMapOfPerson_whenUsingStreamFind_thenShouldReturnElements"() {
        expect:
        personMap.entrySet().stream()
                 .filter { it.key == "Abagail" && it.value.lastname == "Ballard" }
                 .findAny()
                 .isPresent()

        personMap.entrySet().stream()
                 .filter { it.value.age > 20 }
                 .findAll()
                 .size() == 3
    }
}
