package com.baeldung.hexagonal.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.baeldung.hexagonal.fake.FakeBinder;
import java.util.NoSuchElementException;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link PersonServiceImpl} which demonstrate how tests are another adapter to the application.
 */
class PersonServiceImplUnitTest {

    private PersonService service;

    @BeforeEach
    void before() {
        final ServiceLocator locator = ServiceLocatorUtilities.bind(new FakeBinder(), new DomainBinder());
        service = locator.getService(PersonService.class);
    }

    @Test
    void whenSendCreatePersonRequest_thenReturnsNewPerson() {
        final CreatePersonRequest request = new CreatePersonRequest("Joe", "Smith");
        final Person person = service.create(request);

        assertEquals("Joe", person.getFirst());
        assertEquals("Smith", person.getLast());
    }

    @Test
    void givenPersonExists_whenSendGetPersonRequest_thenReturnsPerson() {
        CreatePersonRequest request = new CreatePersonRequest("Joe", "Smith");
        Person person = service.create(request);
        int id = person.getId();

        person = service.get(id);

        assertEquals("Joe", person.getFirst());
        assertEquals("Smith", person.getLast());
    }

    @Test
    void givenPersonDoesNotExist_whenSendGetPersonRequest_thenThrows() {
        assertThrows(NoSuchElementException.class, () -> service.get(10));
    }
}