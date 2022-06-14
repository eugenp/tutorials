package com.baeldung.cloneobjects;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class DeepCloneUnitTest {

    @Test
    public void whenChangingTheFirstPersonContactEmail_thenTheContactEmailsNotMatchAnymore() throws CloneNotSupportedException {

        // creating the address
        Address address = new Address("Victoria Street", "New York", "USA");

        // creating the address
        Contact contact = new Contact("+1234567890", "contact@baeldung.com");

        // creating the firstPerson
        Person firstPerson = new Person("John Smith", 31, address, contact);

        // cloning the firstPerson and assigning it to the secondPerson
        Person secondPerson = (Person) firstPerson.clone();

        // we observe that the firstPerson.Contact.Email is matching the secondPerson Contact email after cloning
        assertEquals(firstPerson.getContact()
            .getEmail(),
            secondPerson.getContact()
                .getEmail());

        // setting a new value for the firstPerson.Contact.Email
        firstPerson.getContact()
            .setEmail("info@baeldung.com");

        // we observe that the firstPerson is referencing the same Contact object which has been initialized with
        assertEquals(firstPerson.getContact()
            .getEmail(), contact.getEmail());

        // we observe that the secondPerson.Contact.Email is not matching anymore the firstPerson.Contact.Email
        assertNotEquals(secondPerson.getContact()
            .getEmail(),
            firstPerson.getContact()
                .getEmail());
    }

}
