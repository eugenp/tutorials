package com.baeldung.copyconstructor;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PersonUnitTest {

    @Test
    public void givenPersonObject_whenCopiedViaClone_thenVerifyOriginalObject() {
        Person originalPerson = new Person(1, "Baeldung", new Address("Paris"));

        Person personCopy = originalPerson.clone();

        personCopy.address.address1 = "London";

        // Verify that the original person object's field has also been modified
        assertEquals("London", originalPerson.address.address1);
    }
}
