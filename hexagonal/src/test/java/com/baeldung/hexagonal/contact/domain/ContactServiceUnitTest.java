package com.baeldung.hexagonal.contact.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

import java.util.UUID;

import com.baeldung.hexagonal.contact.domain.model.NewContactCommand;
import com.baeldung.hexagonal.contact.domain.ports.outgoing.ContactRepository;

class ContactServiceUnitTest {

    private ContactRepository contactRepository;
    private ContactService testedService;

    @BeforeEach
    void setUp() throws Exception {
        contactRepository = mock(ContactRepository.class);
        testedService = new ContactService(contactRepository);
    }

    @Test
    void whenNewContactCommand_thenContactCreated() {
        NewContactCommand command = new NewContactCommand("contact name", "(000) 000-0000");
        UUID uuidToReturn = UUID.randomUUID();
        when(contactRepository.create(command)).thenReturn(uuidToReturn);

        UUID returnedUUID = testedService.handle(command);

        verify(contactRepository).create(command);
        assertEquals(uuidToReturn, returnedUUID);
    }
}
