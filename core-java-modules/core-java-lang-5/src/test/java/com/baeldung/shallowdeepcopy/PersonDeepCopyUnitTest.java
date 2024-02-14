package com.baeldung.deepshallowcopy;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PersonDeepCopyUnitTest {
    @Test
    void whenDeepCopying_thenCopyObjectMakesCopyOfReferencedObjectInSourceObject() {
        AddressDeepCopy address = new AddressDeepCopy("NL");
        PersonDeepCopy originalPerson = new PersonDeepCopy("Maya", address);
        PersonDeepCopy deepCopyOfPerson = null;
        try {
            deepCopyOfPerson = (PersonDeepCopy) originalPerson.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        assertEquals(originalPerson.getAddress().getCountry(), deepCopyOfPerson.getAddress().getCountry());
        assertNotSame(originalPerson.getAddress(), deepCopyOfPerson.getAddress());
        deepCopyOfPerson.getAddress().setCountry("US");
        assertNotEquals(originalPerson.getAddress().getCountry(), deepCopyOfPerson.getAddress().getCountry());
    }
}
