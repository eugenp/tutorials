package com.baeldung.arrays;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Objects;

import org.junit.jupiter.api.Test;

public class ArraysDeepEqualsUnitTest {

    class Person {
        private int id;
        private String name;
        private int age;

        Person(int id, String name, int age) {
            this.id = id;
            this.name = name;
            this.age = age;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (!(obj instanceof Person))
                return false;
            Person person = (Person) obj;
            return id == person.id && name.equals(person.name) && age == person.age;
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, name, age);
        }
    }

    @Test
    void givenTwoUnidimensionalObjectTypeArrays_whenUsingEqualsAndDeepEquals_thenBothShouldReturnTrue() {
        Object[] anArray = new Object[] { "string1", "string2", "string3" };
        Object[] anotherArray = new Object[] { "string1", "string2", "string3" };

        assertTrue(Arrays.equals(anArray, anotherArray));
        assertTrue(Arrays.deepEquals(anArray, anotherArray));
    }

    @Test
    void givenTwoUnidimensionalObjectTypeArraysWithNullElements_whenUsingEqualsAndDeepEquals_thenBothShouldReturnTrue() {
        Object[] anArray = new Object[] { "string1", null, "string3" };
        Object[] anotherArray = new Object[] { "string1", null, "string3" };

        assertTrue(Arrays.equals(anArray, anotherArray));
        assertTrue(Arrays.deepEquals(anArray, anotherArray));
    }

    @Test
    void givenTwoUnidimensionalObjectTypeArraysWithNestedElements_whenUsingEqualsAndDeepEquals_thenShouldReturnDifferently() {
        Object[] anArray = new Object[] { "string1", null, new String[] { "nestedString1", "nestedString2" } };
        Object[] anotherArray = new Object[] { "string1", null, new String[] { "nestedString1", "nestedString2" } };

        assertFalse(Arrays.equals(anArray, anotherArray));
        assertTrue(Arrays.deepEquals(anArray, anotherArray));
    }

    @Test
    void givenTwoMultidimensionalPrimitiveTypeArrays_whenUsingEqualsAndDeepEquals_thenBothShouldReturnDifferently() {
        int[][] anArray = { { 1, 2, 3 }, { 4, 5, 6, 9 }, { 7 } };
        int[][] anotherArray = { { 1, 2, 3 }, { 4, 5, 6, 9 }, { 7 } };

        assertFalse(Arrays.equals(anArray, anotherArray));
        assertTrue(Arrays.deepEquals(anArray, anotherArray));
    }

    @Test
    void givenTwoMultidimensionalObjectTypeArrays_whenUsingEqualsAndDeepEquals_thenBothShouldReturnDifferently() {
        Person personArray1[][] = { { new Person(1, "John", 22), new Person(2, "Mike", 23) }, { new Person(3, "Steve", 27), new Person(4, "Gary", 28) } };
        Person personArray2[][] = { { new Person(1, "John", 22), new Person(2, "Mike", 23) }, { new Person(3, "Steve", 27), new Person(4, "Gary", 28) } };

        assertFalse(Arrays.equals(personArray1, personArray2));
        assertTrue(Arrays.deepEquals(personArray1, personArray2));
    }

    @Test
    void givenTwoMultidimensionalObjectTypeArrays_whenUsingDeepEqualsFromObjectsAndArraysClasses_thenBothShouldReturnTrue() {
        Person personArray1[][] = { { new Person(1, "John", 22), new Person(2, "Mike", 23) }, { new Person(3, "Steve", 27), new Person(4, "Gary", 28) } };
        Person personArray2[][] = { { new Person(1, "John", 22), new Person(2, "Mike", 23) }, { new Person(3, "Steve", 27), new Person(4, "Gary", 28) } };

        assertTrue(Objects.deepEquals(personArray1, personArray2));
        assertTrue(Arrays.deepEquals(personArray1, personArray2));
    }
}
