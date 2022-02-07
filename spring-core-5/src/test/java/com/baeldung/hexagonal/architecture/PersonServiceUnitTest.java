package com.baeldung.hexagonal.architecture;

import com.baeldung.hexagonal.architecture.adapters.driving.PersonServiceImpl;
import com.baeldung.hexagonal.architecture.core.domain.Person;
import com.baeldung.hexagonal.architecture.core.ports.inbound.PersonService;
import com.baeldung.hexagonal.architecture.core.ports.outbound.PersonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PersonServiceUnitTest {
    private PersonService personService;
    private PersonRepository personRepository;

    @BeforeEach
    public void setUp() {
        personRepository = mock(PersonRepository.class);
        personService = new PersonServiceImpl(personRepository);
    }

    @Test
    public void shouldGetPersonById() {
        long id = anyLong();
        when(personRepository.findById(id)).thenReturn(mock(Person.class));

        Person person = personService.findById(id);

        Assertions.assertNotNull(person);
    }
}
