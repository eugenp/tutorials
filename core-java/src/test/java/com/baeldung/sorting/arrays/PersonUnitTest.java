package com.baeldung.sorting.arrays;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

public class PersonUnitTest {

    @Test
    public void givenThatTwoPersonsHasDifferentLastName_whenAnArrayIsSorted_thenTheResultIsTheCorrectOne() {

        Person[] persons = new Person[2];

        persons[0] = new Person("FirstName","BbLastName",10 );
        persons[1] = new Person("FirstName","AaLastName",10 );

        Comparator<Person> personComparator = Comparator.comparing(Person::getFirstName)
                                                        .thenComparing(Person::getLastName)
                                                        .thenComparingInt(Person::getAge);

        List<Person> personsList = Arrays.asList(persons);
        personsList = personsList.stream().sorted(personComparator).collect(Collectors.toList());
        persons = personsList.toArray(persons);
        assert  persons[0].getLastName().equals("AaLastName");
        assert  persons[1].getLastName().equals("BbLastName");
    }

    @Test
    public void givenThatTwoPersonsHasDifferentFirst_whenAnArrayIsSorted_thenTheResultIsTheCorrectOne() {

        Person[] persons = new Person[2];

        persons[0] = new Person("BbFirstName","LastName",10 );
        persons[1] = new Person("AaFirstName","LastName",10 );

        Comparator<Person> personComparator = Comparator.comparing(Person::getFirstName)
                                                        .thenComparing(Person::getLastName)
                                                        .thenComparingInt(Person::getAge);

        List<Person> personsList = Arrays.asList(persons);
        personsList = personsList.stream().sorted(personComparator).collect(Collectors.toList());
        persons = personsList.toArray(persons);
        assert  persons[0].getFirstName().equals("AaFirstName");
        assert  persons[1].getFirstName().equals("BbFirstName");
    }

    @Test
    public void givenThatTwoPersonsHasDifferentAge_whenAnArrayIsSorted_thenTheResultIsTheCorrectOne() {

        Person[] persons = new Person[2];

        persons[0] = new Person("FirstName","LastName",12 );
        persons[1] = new Person("FirstName","LastName",15 );

        Comparator<Person> personComparator = Comparator.comparing(Person::getFirstName)
                                                        .thenComparing(Person::getLastName)
                                                        .thenComparingInt(Person::getAge);

        List<Person> personsList = Arrays.asList(persons);
        personsList = personsList.stream().sorted(personComparator).collect(Collectors.toList());
        persons = personsList.toArray(persons);
        assert  persons[0].getAge() == 12;
        assert  persons[1].getAge() == 15;
    }

}
