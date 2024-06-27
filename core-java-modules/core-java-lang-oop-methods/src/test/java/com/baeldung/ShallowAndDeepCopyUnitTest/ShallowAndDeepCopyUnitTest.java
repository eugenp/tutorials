package com.baeldung.ShallowAndDeepCopyUnitTest;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ShallowAndDeepCopyUnitTest {
    @Test
    public void whenShallowApproachUsed_ThenAssertTrue() throws CloneNotSupportedException {
        Address address = new Address("New York");
        Person original = new Person("John Doe", address);
        Person shallowCopy = (Person) original.clone();

        original.address.city = "Los Angeles";

        assertEquals("Los Angeles", shallowCopy.address.city);
    }

    @Test
    public void whenDeepCopyUsed_ThenAssertTrue() {
        Address address = new Address("New York");
        Person original = new Person("John Doe", address);
        Person deepCopy = original.deepCopy();

        original.address.city = "Los Angeles";

        assertEquals("New York", deepCopy.address.city);
    }


}
