package com.baeldung.java14.record;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PersonTest {

    @Test
    public void givenSameNameAndAddress_whenEquals_thenPersonsEqual() {

        String name = "John Doe";
        String address = "100 Linda Ln.";

        Person person1 = new Person(name, address);
        Person person2 = new Person(name, address);

        assertTrue(person1.equals(person2));
    }
    
    @Test
    public void givenDifferentObject_whenEquals_thenNotEqual() {

        Person person = new Person("John Doe", "100 Linda Ln.");

        assertFalse(person.equals(new Object()));
    }

    @Test
    public void givenDifferentName_whenEquals_thenPersonsNotEqual() {
    
        String address = "100 Linda Ln.";
    
        Person person1 = new Person("Jane Doe", address);
        Person person2 = new Person("John Doe", address);
    
        assertFalse(person1.equals(person2));
    }
    
    @Test
    public void givenDifferentAddress_whenEquals_thenPersonsNotEqual() {
    
        String name = "John Doe";
    
        Person person1 = new Person(name, "100 Linda Ln.");
        Person person2 = new Person(name, "200 London Ave.");
    
        assertFalse(person1.equals(person2));
    }

    @Test
    public void givenSameNameAndAddress_whenHashCode_thenPersonsEqual() {

        String name = "John Doe";
        String address = "100 Linda Ln.";

        Person person1 = new Person(name, address);
        Person person2 = new Person(name, address);

        assertEquals(person1.hashCode(), person2.hashCode());
    }
    
    @Test
    public void givenDifferentObject_whenHashCode_thenNotEqual() {

        Person person = new Person("John Doe", "100 Linda Ln.");

        assertNotEquals(person.hashCode(), new Object().hashCode());
    }

    @Test
    public void givenDifferentName_whenHashCode_thenPersonsNotEqual() {

        String address = "100 Linda Ln.";

        Person person1 = new Person("Jane Doe", address);
        Person person2 = new Person("John Doe", address);

        assertNotEquals(person1.hashCode(), person2.hashCode());
    }

    @Test
    public void givenDifferentAddress_whenHashCode_thenPersonsNotEqual() {

        String name = "John Doe";

        Person person1 = new Person(name, "100 Linda Ln.");
        Person person2 = new Person(name, "200 London Ave.");

        assertNotEquals(person1.hashCode(), person2.hashCode());
    }

    @Test
    public void givenValidNameAndAddress_whenGetNameAndAddress_thenExpectedValuesReturned() {

        String name = "John Doe";
        String address = "100 Linda Ln.";

        Person person = new Person(name, address);

        assertEquals(name, person.name());
        assertEquals(address, person.address());
    }

    @Test
    public void givenValidNameAndAddress_whenToString_thenCorrectStringReturned() {

        String name = "John Doe";
        String address = "100 Linda Ln.";

        Person person = new Person(name, address);

        assertEquals("Person[name=" + name + ", address=" + address + "]", person.toString());
    }

    @Test(expected = NullPointerException.class)
    public void givenNullName_whenConstruct_thenErrorThrown() {
        new Person(null, "100 Linda Ln.");
    }

    @Test(expected = NullPointerException.class)
    public void givenNullAddress_whenConstruct_thenErrorThrown() {
        new Person("John Doe", null);
    }

    @Test
    public void givenUnknownAddress_whenConstructing_thenAddressPopulated() {

        String name = "John Doe";

        Person person = new Person(name);

        assertEquals(name, person.name());
        assertEquals(Person.UNKNOWN_ADDRESS, person.address());
    }

    @Test
    public void givenUnnamed_whenConstructingThroughFactory_thenNamePopulated() {

        String address = "100 Linda Ln.";

        Person person = Person.unnamed(address);

        assertEquals(Person.UNNAMED, person.name());
        assertEquals(address, person.address());
    }
}
