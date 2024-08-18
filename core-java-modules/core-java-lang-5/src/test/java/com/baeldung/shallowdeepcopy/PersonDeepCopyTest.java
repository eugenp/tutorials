package com.baeldung.shallowdeepcopy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonDeepCopyTest {

    @Test
    public void whenDeepCopyingPerson_thenAddressIsDeepCopied() {
        AddressDeepCopy address = new AddressDeepCopy("New York");
        PersonDeepCopy person1 = new PersonDeepCopy("John", address);
        PersonDeepCopy person2 = person1.clone();

        person2.address.city = "San Francisco";

        assertEquals("New York", person1.address.city);
    }
}