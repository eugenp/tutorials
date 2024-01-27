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
        //a and b are different objects on the heap.
        assertNotEquals(a, b);
        //a.house and b.house are different objects on the heap.
        assertNotEquals(a.getHouse(), b.getHouse());
        assertEquals(a.getHouse()
            .getAddress(), b.getHouse()
            .getAddress()); // houses have same addresses
    }

    @Test
    void deep_copy_mutated_inner() {
        Person a = person;
        Person b = person.deepCopy();
        //A and B refer to the different house object
        assertNotEquals(a.getHouse(), b.getHouse());
        // mutate one copy
        House h = b.getHouse();
        h.setAddress("New House");
        // changes in inner objects are isolated.
        assertNotEquals("New House", a.getHouse()
            .getAddress());
    }

}