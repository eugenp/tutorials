package com.baeldung.find

import com.baeldung.find.Person
import org.junit.Test

import static org.junit.Assert.*

class ListFindUnitTest {

    private final personList = [
      new Person("Regina", "Fitzpatrick", 25),
      new Person("Abagail", "Ballard", 26),
      new Person("Lucian", "Walter", 30),
    ]

    @Test
    void whenListContainsElement_thenCheckReturnsTrue() {
        def list = ['a', 'b', 'c']

        assertTrue(list.indexOf('a') > -1)
        assertTrue(list.contains('a'))
    }

    @Test
    void whenListContainsElement_thenCheckWithMembershipOperatorReturnsTrue() {
        def list = ['a', 'b', 'c']

        assertTrue('a' in list)
    }

    @Test
    void givenListOfPerson_whenUsingStreamMatching_thenShouldEvaluateList() {
        assertTrue(personList.stream().anyMatch {it.age > 20})
        assertFalse(personList.stream().allMatch {it.age < 30})
    }

    @Test
    void givenListOfPerson_whenUsingCollectionMatching_thenShouldEvaluateList() {
        assertTrue(personList.any {it.age > 20})
        assertFalse(personList.every {it.age < 30})
    }

    @Test
    void givenListOfPerson_whenUsingStreamFind_thenShouldReturnMatchingElements() {
        assertTrue(personList.stream().filter {it.age > 20}.findAny().isPresent())
        assertFalse(personList.stream().filter {it.age > 30}.findAny().isPresent())
        assertTrue(personList.stream().filter {it.age > 20}.findAll().size() == 3)
        assertTrue(personList.stream().filter {it.age > 30}.findAll().isEmpty())
    }

    @Test
    void givenListOfPerson_whenUsingCollectionFind_thenShouldReturnMatchingElements() {
        assertNotNull(personList.find {it.age > 20})
        assertNull(personList.find {it.age > 30})
        assertTrue(personList.findAll {it.age > 20}.size() == 3)
        assertTrue(personList.findAll {it.age > 30}.isEmpty())
    }
}
