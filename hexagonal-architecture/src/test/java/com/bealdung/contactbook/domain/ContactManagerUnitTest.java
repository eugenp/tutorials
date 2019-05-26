package com.bealdung.contactbook.domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ContactManagerUnitTest {

    @Mock
    private ContactRepository contactRepositoryMock;

    private ContactManager contactManager;

    @Before
    public void setup() {
        contactManager = new ContactManager(contactRepositoryMock);
    }

    @Test
    public void givenValidContact_whenSaving_thenRepositoryIsCalled() {
        // given
        Contact contact = Contact.builder().name("John").email("test@email.com").build();

        // when
        contactManager.save(contact);

        // then
        verify(contactRepositoryMock, times(1)).save(contact);
    }

    @Test(expected = ContactNotValidException.class)
    public void givenNotValidContact_whenSaving_thenExceptionIsRaised() {
        // given
        Contact contactWithNoName = Contact.builder().email("test@email.com").build();

        // when
        contactManager.save(contactWithNoName);

        // then - expected exception
    }
}