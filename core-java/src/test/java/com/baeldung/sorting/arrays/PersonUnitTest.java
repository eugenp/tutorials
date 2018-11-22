package com.baeldung.sorting.arrays;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.junit.Assert.*;


import org.junit.Test;

public class PersonUnitTest {

    @Test
    public void givenThatAPersonWithLastNameWithGreaterAlphabeticOrderThanAnotherPerson_whenAnArrayIncludingThoseTwoPersonsIsSorted_thenTheGreaterAlphabeticOrderPersonIsFirst() {

        Person[] persons = new Person[2];

        persons[0] = new Person("FirstName","BbLastName",10 );
        persons[1] = new Person("FirstName","AaLastName",10 );


        List<Person> personsList = Arrays.asList(persons);
        Collections.sort(personsList);
        persons = personsList.toArray(persons);
        assertEquals(persons[0].getLastName(), "AaLastName");
        assertEquals(persons[1].getLastName(), "BbLastName");
    }

    @Test
    public void givenThatAPersonWithFirstNameWithGreaterAlphabeticOrderThanAnotherPerson_whenAnArrayIncludingThoseTwoPersonsIsSorted_thenTheGreaterAlphabeticOrderPersonIsFirst() {

        Person[] persons = new Person[2];

        persons[0] = new Person("BbFirstName","LastName",10 );
        persons[1] = new Person("AaFirstName","LastName",10 );


        List<Person> personsList = Arrays.asList(persons);
        Collections.sort(personsList);
        persons = personsList.toArray(persons);
        assertEquals(persons[0].getFirstName(),"AaFirstName");
        assertEquals(persons[1].getFirstName(),"BbFirstName");
    }

    @Test
    public void givenThatAPersonWithAgeGreaterAgeThanAnotherPerson_whenAnArrayIncludingThoseTwoPersonsIsSorted_thenTheGreaterAgePersonIsFirst() {

        Person[] persons = new Person[2];

        persons[0] = new Person("FirstName","LastName",12 );
        persons[1] = new Person("FirstName","LastName",15 );


        List<Person> personsList = Arrays.asList(persons);
        Collections.sort(personsList);
        persons = personsList.toArray(persons);
        assertEquals(persons[0].getAge(),12);
        assertEquals(persons[1].getAge(),15);
    }

}
