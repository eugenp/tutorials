package com.baeldung.deepshallowcopy;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ShallowCopyTest {

    @Test
    public void givenShallowCopy_whenCopyIsModified_originalObjectIsAlsoModified() throws CloneNotSupportedException {
        ShallowAddress shallowAddress = new ShallowAddress("SomeStreet", "City");
        ShallowPerson shallowPerson = new ShallowPerson("Name", "Surname", shallowAddress);
        ShallowPerson clonedPerson = (ShallowPerson) shallowPerson.clone();
        shallowAddress.setStreet("AnotherStreet");

        assertEquals(shallowPerson.getAddress().getStreet(), "AnotherStreet");
        assertEquals(clonedPerson.getAddress().getStreet(), "AnotherStreet");
    }
}
