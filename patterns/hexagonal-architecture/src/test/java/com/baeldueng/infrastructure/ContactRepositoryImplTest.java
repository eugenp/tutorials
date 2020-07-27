package com.baeldueng.infrastructure;

import com.baeldueng.domain.Contact;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class ContactRepositoryImplTest {


    @Test
    void givenContact_whenSaved_thenReturnSavedContact() {
        //Assemble
        ContactRepositoryImpl contactRepository = new ContactRepositoryImpl();
        Contact contact = new Contact("name", "mobileNumber");
        contact.setId("id");

        //Act
        Contact savedContact = contactRepository.save(contact);

        //Assert
        Assert.assertEquals(contact.getId(), savedContact.getId());
        Assert.assertEquals(contact.getMobileNumber(), savedContact.getMobileNumber());
        Assert.assertEquals(contact.getName(), savedContact.getName());
    }

    @Test
    void givenContactId_whenRetrievingById_thenReturnSavedContact() {
        //Assemble
        ContactRepositoryImpl contactRepository = new ContactRepositoryImpl();
        Contact contact = new Contact("name", "mobileNumber");
        contact.setId("id");
        contact.setMobileNumber("mobileNumber");
        contact.setName("name");
        contactRepository.save(contact);

        //Act
        Contact savedContact = contactRepository.getById(contact.getId());

        //Assert
        Assert.assertEquals(contact.getId(), savedContact.getId());
        Assert.assertEquals(contact.getMobileNumber(), savedContact.getMobileNumber());
        Assert.assertEquals(contact.getName(), savedContact.getName());
    }
}