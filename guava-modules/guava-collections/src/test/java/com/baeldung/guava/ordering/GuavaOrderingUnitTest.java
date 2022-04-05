package com.baeldung.guava.ordering;

import com.google.common.base.Function;
import com.google.common.collect.Ordering;
import com.google.common.primitives.Ints;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class GuavaOrderingUnitTest {
    @Test
    public void givenListOfIntegers_whenCreateNaturalOrderOrdering_shouldSortProperly() {
        //given
        List<Integer> integers = Arrays.asList(3, 2, 1);

        //when
        integers.sort(Ordering.natural());

        //then
        assertEquals(Arrays.asList(1, 2, 3), integers);
    }

    @Test
    public void givenListOfPersonObject_whenSortedUsingCustomOrdering_shouldSortProperly() {
        //given
        List<Person> persons = Arrays.asList(new Person("Michael", 10), new Person("Alice", 3));
        Ordering<Person> orderingByAge = new Ordering<Person>() {
            @Override
            public int compare(Person p1, Person p2) {
                return Ints.compare(p1.age, p2.age);
            }
        };

        //when
        persons.sort(orderingByAge);

        //then
        assertEquals(Arrays.asList(new Person("Alice", 3), new Person("Michael", 10)), persons);
    }

    @Test
    public void givenListOfPersonObject_whenSortedUsingChainedOrdering_shouldSortPropely() {
        //given
        List<Person> persons = Arrays.asList(new Person("Michael", 10), new Person("Alice", 3), new Person("Thomas", null));
        Ordering<Person> ordering = Ordering.natural().nullsFirst().onResultOf(new Function<Person, Comparable>() {
            @Override
            public Comparable apply(Person person) {
                return person.age;
            }
        });

        //when
        persons.sort(ordering);

        //then
        assertEquals(Arrays.asList(new Person("Thomas", null), new Person("Alice", 3), new Person("Michael", 10)), persons);
    }


    class Person {
        private final String name;
        private final Integer age;

        private Person(String name, Integer age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Person person = (Person) o;

            if (name != null ? !name.equals(person.name) : person.name != null) return false;
            return age != null ? age.equals(person.age) : person.age == null;

        }

        @Override
        public int hashCode() {
            int result = name != null ? name.hashCode() : 0;
            result = 31 * result + (age != null ? age.hashCode() : 0);
            return result;
        }
    }
}
