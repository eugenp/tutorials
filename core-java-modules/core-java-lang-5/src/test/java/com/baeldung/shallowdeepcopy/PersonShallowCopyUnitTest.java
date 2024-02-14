package com.baeldung.deepshallowcopy;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PersonShallowCopyUnitTest {
    @Test
    void whenShallowCopying_thenCopyObjectAndSourceObjectShareSameReferencedObject() {
        Address address = new Address("NL");
        Person person = new Person("Maya", address);
        Person shallowCopyOfPerson = null;
        try {
            shallowCopyOfPerson = (Person) person.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        assertSame(person.getName(), shallowCopyOfPerson.getName());
        assertSame(person.getAddress(), shallowCopyOfPerson.getAddress());
        shallowCopyOfPerson.getAddress().setCountry("US");
        assertEquals(person.getAddress().getCountry(), shallowCopyOfPerson.getAddress().getCountry());
    }
}
