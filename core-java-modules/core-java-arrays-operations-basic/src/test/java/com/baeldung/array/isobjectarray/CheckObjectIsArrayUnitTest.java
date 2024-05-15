package com.baeldung.array.isobjectarray;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Array;

import org.junit.jupiter.api.Test;

public class CheckObjectIsArrayUnitTest {
    private static final Object ARRAY_INT = new int[] { 1, 2, 3, 4, 5 };
    private static final Object ARRAY_PERSON = new Person[] { new Person("Jackie Chan", "Hong Kong"), new Person("Tom Hanks", "United States") };

    boolean isArray(Object obj) {
        return obj instanceof Object[] || obj instanceof boolean[] || obj instanceof byte[] || obj instanceof short[] || obj instanceof char[] || obj instanceof int[] || obj instanceof long[] || obj instanceof float[] || obj instanceof double[];
    }

    @Test
    void givenAnArrayObject_whenUsingInstanceof_getExpectedResult() {
        assertTrue(ARRAY_PERSON instanceof Object[]);
        assertFalse(ARRAY_INT instanceof Object[]);
        assertTrue(ARRAY_INT instanceof int[]);
    }

    @Test
    void givenAnArrayObject_whenUsingOurIsArray_getExpectedResult() {
        assertTrue(isArray(ARRAY_PERSON));
        assertTrue(isArray(ARRAY_INT));
    }

    @Test
    void givenAnArrayObject_whenUsingClassIsArray_getExpectedResult() {
        assertTrue(ARRAY_INT.getClass()
          .isArray());
        assertTrue(ARRAY_PERSON.getClass()
          .isArray());

        assertEquals(Person.class, ARRAY_PERSON.getClass()
          .getComponentType());
        assertEquals(int.class, ARRAY_INT.getClass()
          .getComponentType());

    }

    @Test
    void givenAnArrayObject_whenUsingArrayGet_getExpectedElement() {
        if (ARRAY_PERSON.getClass()
          .isArray() && ARRAY_PERSON.getClass()
          .getComponentType() == Person.class) {
            Person person = (Person) Array.get(ARRAY_PERSON, 1);
            assertEquals("Tom Hanks", person.getName());
        }
        if (ARRAY_INT.getClass()
          .isArray() && ARRAY_INT.getClass()
          .getComponentType() == int.class) {
            assertEquals(2, ((int) Array.get(ARRAY_INT, 1)));
        }
    }
}

class Person {
    private String name;
    private String Location;

    public Person(String name, String location) {
        this.name = name;
        this.Location = location;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return Location;
    }
}