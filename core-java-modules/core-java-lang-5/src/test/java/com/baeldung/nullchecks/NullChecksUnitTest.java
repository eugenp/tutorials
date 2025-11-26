package com.baeldung.nullchecks;

import org.junit.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class NullChecksUnitTest {

    @Test
    public void whenReferenceEqualityOnPrimitives_thenCompareValues() {
        int a = 10;
        int b = 15;
        int c = 10;
        int d = a;

        // different values check
        assertFalse(a == b);

        // same values check
        assertTrue(a == c);

        // same references check
        assertTrue(a == d);
    }

    @Test
    public void whenReferenceEqualityOnObjects_thenCompareReferences() {
        Person a = new Person("Bob", 20);
        Person b = new Person("Mike", 40);
        Person c = new Person("Bob", 20);
        Person d = a;
        Person e = null;

        // different values check
        assertFalse(a == b);

        // same values check
        assertFalse(a == c);

        // same references check
        assertTrue(a == d);

        // same references check - for nulls
        assertTrue(e == null);
    }

    @Test
    public void whenValueEqualityOnPrimitives_thenCompareValues() {
        int a = 10;
        Integer b = a;

        assertTrue(b.equals(10));
    }

    @Test
    public void whenValueEqualityOnObjects_thenCompareValues() {
        Person a = new Person("Bob", 20);
        Person b = new Person("Mike", 40);
        Person c = new Person("Bob", 20);
        Person d = a;
        Person e = null;

        // different values check
        assertFalse(a.equals(b));

        // same values check
        assertTrue(a.equals(c));

        // same references check
        assertTrue(a.equals(d));

        // null checks
        assertFalse(a.equals(e));
        assertThrows(NullPointerException.class, () -> e.equals(a));

        // null checks fixed
        assertFalse(e != null && e.equals(a));

        // using Objects.equals
        assertFalse(Objects.equals(e, a));
        assertTrue(Objects.equals(null, e));

    }

    @Test
    public void givenEqualObjects_whenHashCodesMatch_thenCollectionsBehaveCorrectly() {
        Person a = new Person("Bob", 20);
        Person b = new Person("Bob", 20);
        assertTrue(a.equals(b));
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void givenStrings_whenComparingReferences_thenStringPoolAffectsEquality() {
        String a = "hello";
        String b = "hello";
        assertTrue(a == b); // both point to the same pooled instance

        String c = new String("hello");
        assertFalse(a == c); // c isn't taken from the pool
    }

    private class Person {

        private String name;
        private int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Person person = (Person) o;
            return age == person.age && Objects.equals(name, person.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, age);
        }
    }
}
