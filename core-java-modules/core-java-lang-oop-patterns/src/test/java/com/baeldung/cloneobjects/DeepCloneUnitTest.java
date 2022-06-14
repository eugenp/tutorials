package com.baeldung.cloneobjects;

import static org.junit.Assert.assertNotEquals;

import org.junit.jupiter.api.Test;

class DeepCloneUnitTest {

    @Test
    void whenChangingTheFirstPersonContactEmail_thenTheContactEmailsNotMatchAnymore() throws CloneNotSupportedException {

        Address address = new Address("Victoria Street", "New York", "USA");
        Contact contact = new Contact("+1234567890", "contact@baeldung.com");
        Person firstPerson = new Person("John Smith", 31, address, contact);
        Person secondPerson = (Person) firstPerson.clone();

        firstPerson.getContact()
            .setEmail("info@baeldung.com");

        assertNotEquals(firstPerson.getContact()
            .getEmail(),
            secondPerson.getContact()
                .getEmail());
    }
}
