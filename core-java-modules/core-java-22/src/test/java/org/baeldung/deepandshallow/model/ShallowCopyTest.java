package org.baeldung.deepandshallow.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShallowCopyTest {

    private Person person;

    @BeforeEach
    public void init() {
        person = new Person();
        person.setAge(47);
        person.setName("Sherlock");
        House house = new House();
        house.setAddress("221B Baker Street");
        person.setHouse(house);
    }

    @Test
    void shallow_copy_with_clone() throws CloneNotSupportedException {
        Person a = person;
        Person b = person.clone();
        assertNotEquals(a, b);
        assertEquals(a.getName(), b.getName());
        assertEquals(a.getAge(), b.getAge());
        assertEquals(a.getHouse(), b.getHouse());
    }

    @Test
    void shallow_copy_mutated_inner() throws CloneNotSupportedException {
        Person a = person;
        Person b = person.clone();
        House h = b.getHouse();
        h.setAddress("New House");
        assertEquals("New House",
          a.getHouse().getAddress());
    }

}