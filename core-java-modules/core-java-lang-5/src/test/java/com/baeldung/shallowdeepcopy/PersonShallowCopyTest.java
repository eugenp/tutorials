package com.baeldung.shallowdeepcopy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonShallowCopyTest {
    @Test
    public void testShallowCopy() throws CloneNotSupportedException {
        AddressShallowCopy address = new AddressShallowCopy("New York");
        PersonShallowCopy person1 = new PersonShallowCopy("John", address);
        PersonShallowCopy person2 = (PersonShallowCopy) person1.clone();

        person2.address.city = "San Francisco";

        assertEquals("San Francisco", person1.address.city);
    }
}