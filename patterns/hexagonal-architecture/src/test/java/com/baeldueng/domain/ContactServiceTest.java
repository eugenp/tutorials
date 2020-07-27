package com.baeldueng.domain;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.when;

@SpringBootTest
class ContactServiceTest {

    @Test
    void givenContact_whenCreateContactInvoked_thenSaveContact() {
        ContactRepository contactRepository = Mockito.mock(ContactRepository.class);
        ContactService contactService = new ContactService(contactRepository);
        Contact contact = new Contact("name", "mobile");
        when(contactRepository.save(contact)).thenReturn(contact);

        Contact savedContact = contactService.createContact(contact);

        Assert.assertEquals(contact, savedContact);
    }
}