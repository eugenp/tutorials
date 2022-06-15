package com.baeldung.deepshallowcopy;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DeepCopyTest {

    @Test
    public void givenDeepCopy_whenCopyIsModified_originalObjectIsNotModified() throws CloneNotSupportedException {
        DeepAddress deepAddress = new DeepAddress("SomeStreet", "City");
        DeepPerson deepPerson = new DeepPerson("Name", "Surname", deepAddress);
        DeepPerson clonedPerson = (DeepPerson) deepPerson.clone();
        deepAddress.setStreet("AnotherStreet");

        assertEquals(deepPerson.getAddress().getStreet(), "AnotherStreet");
        assertEquals(clonedPerson.getAddress().getStreet(), "SomeStreet");
    }
}
