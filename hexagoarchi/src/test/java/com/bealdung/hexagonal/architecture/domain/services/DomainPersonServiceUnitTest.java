package com.bealdung.hexagonal.architecture.domain.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import com.bealdung.hexagonal.architecture.domain.model.Person;
import com.bealdung.hexagonal.architecture.domain.repository.PersonRepositoryPort;
import com.bealdung.hexagonal.architecture.domain.service.DomainPersonService;

public class DomainPersonServiceUnitTest {

    private PersonRepositoryPort personRepositoryPort;
    private DomainPersonService domainPersonService;

    @Before
    public void setUp() {
        personRepositoryPort = mock(PersonRepositoryPort.class);
        domainPersonService = new DomainPersonService(personRepositoryPort);
    }

    @Test
    public void shouldCreatePerson_thenSaveIt() {
        Person person = new Person();
        person.setId(1);
        person.setFirstName("Achraf");
        person.setLastName("Taitai");
        given(personRepositoryPort.save(person)).willReturn(person);

        assertThat(domainPersonService.save(person)).isSameAs(person);
    }

    @Test
    public void shouldFindPersonById_thenReturnIt() {
        Person person = new Person();
        person.setId(1);
        person.setFirstName("Achraf");
        person.setLastName("Taitai");
        when(personRepositoryPort.findById(1)).thenReturn(Optional.of(person));

        assertThat(domainPersonService.findById(1)).isEqualTo(Optional.of(person));
    }

}
