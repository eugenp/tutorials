package com.baeldung.find

import spock.lang.Specification

class ListFindUnitTest extends Specification {

    final personList = [
            new Person("Regina", "Fitzpatrick", 25),
            new Person("Abagail", "Ballard", 26),
            new Person("Lucian", "Walter", 30),
    ]

    def "whenListContainsElement_thenCheckReturnsTrue"() {
        given:
        def list = ['a', 'b', 'c']

        expect:
        list.indexOf('a') > -1
        list.contains('a')
    }

    def "whenListContainsElement_thenCheckWithMembershipOperatorReturnsTrue"() {
        given:
        def list = ['a', 'b', 'c']

        expect:
        'a' in list
    }

    def "givenListOfPerson_whenUsingStreamMatching_thenShouldEvaluateList"() {
        expect:
        personList.stream().anyMatch { it.age > 20 }
        !personList.stream().allMatch { it.age < 30 }
    }

    def "givenListOfPerson_whenUsingCollectionMatching_thenShouldEvaluateList"() {
        expect:
        personList.any { it.age > 20 }
        !personList.every { it.age < 30 }
    }

    def "givenListOfPerson_whenUsingStreamFind_thenShouldReturnMatchingElements"() {
        expect:
        personList.stream().filter { it.age > 20 }.findAny().isPresent()
        !personList.stream().filter { it.age > 30 }.findAny().isPresent()
        personList.stream().filter { it.age > 20 }.findAll().size() == 3
        personList.stream().filter { it.age > 30 }.findAll().isEmpty()
    }

    def "givenListOfPerson_whenUsingCollectionFind_thenShouldReturnMatchingElements"() {
        expect:
        personList.find { it.age > 20 } == new Person("Regina", "Fitzpatrick", 25)
        personList.find { it.age > 30 } == null
        personList.findAll { it.age > 20 }.size() == 3
        personList.findAll { it.age > 30 }.isEmpty()
    }
}
