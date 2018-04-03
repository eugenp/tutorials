package com.baeldung.java8;

import com.baeldung.java_8_features.Car;
import com.baeldung.java_8_features.Person;

import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;

public class Java8MaxMinUnitTest {

    @Test
    public void whenListIsOfIntegerThenMaxCanBeDoneUsingIntegerComparator() {
        //given
        final List<Integer> listOfIntegers = Arrays.asList(1, 2, 3, 4, 56, 7, 89, 10);
        final Integer expectedResult = 89;

        //then
        final Integer max = listOfIntegers
          .stream()
          .mapToInt(v -> v)
          .max().orElseThrow(NoSuchElementException::new);

        assertEquals("Should be 89", expectedResult, max);
    }

    @Test
    public void whenListIsOfPersonObjectThenMinCanBeDoneUsingCustomComparatorThroughLambda() {
        //given
        final Person alex = new Person("Alex", 23);
        final Person john = new Person("John", 40);
        final Person peter = new Person("Peter", 32);
        final List<Person> people = Arrays.asList(alex, john, peter);

        //then
        final Person minByAge = people
          .stream()
          .min(Comparator.comparing(Person::getAge))
          .orElseThrow(NoSuchElementException::new);

        assertEquals("Should be Alex", alex, minByAge);
    }

    @Test
    public void whenArrayIsOfIntegerThenMinUsesIntegerComparator() {
        int[] integers = new int[] { 20, 98, 12, 7, 35 };

        int min = Arrays.stream(integers)
            .min()
            .getAsInt();

        assertEquals(7, min);
    }

    @Test
    public void whenArrayIsOfCustomTypeThenMaxUsesCustomComparator() {
        final Car porsche = new Car("Porsche 959", 319);
        final Car ferrari = new Car("Ferrari 288 GTO", 303);
        final Car bugatti = new Car("Bugatti Veyron 16.4 Super Sport", 415);
        final Car mcLaren = new Car("McLaren F1", 355);
        final Car[] fastCars = { porsche, ferrari, bugatti, mcLaren };

        final Car maxBySpeed = Arrays.stream(fastCars)
            .max(Comparator.comparing(Car::getTopSpeed))
            .orElseThrow(NoSuchElementException::new);

        assertEquals(bugatti, maxBySpeed);
    }
}
