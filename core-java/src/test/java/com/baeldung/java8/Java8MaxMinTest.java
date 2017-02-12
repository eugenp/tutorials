package com.baeldung.java8;

import com.baeldung.java_8_features.Person;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

import static org.junit.Assert.assertEquals;

public class Java8MaxMinTest {

    @Test
    public void whenListIsOfIntegerThenMaxCanBeDoneUsingIntegerComparator() {
        //given
        final List<Integer> listOfIntegers = Arrays.asList(1, 2, 3, 4, 56, 7, 89, 10);
        final Integer expectedResult = 89;

        //when
        OptionalInt result = listOfIntegers.stream().mapToInt(val -> val).max();
        Integer resultMax = result.isPresent() ? result.getAsInt() : null;

        //then
        assertEquals(expectedResult, resultMax);
    }

    @Test
    public void whenListIsOfPersonObjectThenMinCanBeDoneUsingCustomComparatorThroughLambda() {
        //given
        final Person alex = new Person("Alex", 23);
        final Person john = new Person("John", 40);
        final Person peter = new Person("Peter", 32);
        final List<Person> people = Arrays.asList(alex, john, peter);

        //when
        Optional<Person> minAge =
          people.stream().min((o1, o2) -> o1.getAge().compareTo(o2.getAge()));
        Person resultMin = minAge.isPresent() ? minAge.get() : null;

        //then
        assertEquals("Alex must be having min age", alex, resultMin);
    }

}
