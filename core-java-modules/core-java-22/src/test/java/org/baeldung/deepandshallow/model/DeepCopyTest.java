package org.baeldung.deepandshallow.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class DeepCopyTest {

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
    void deep_copy() {
        Person a = person;
        Person b = person.deepCopy();
        assertNotEquals(a, b);
        assertNotEquals(a.getHouse(), b.getHouse());
        assertEquals(a.getHouse().getAddress(),
          b.getHouse().getAddress());
    }

    @Test
    void deep_copy_mutated_inner() {
        Person a = person;
        Person b = person.deepCopy();
        assertNotEquals(a.getHouse(), b.getHouse());
        House h = b.getHouse();
        h.setAddress("New House");
        assertNotEquals("New House",
          a.getHouse().getAddress());
    }

}