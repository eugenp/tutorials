package com.baeldung.patterns.hexagonalarchitecture.people.evidence.application.port.primary;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.baeldung.patterns.hexagonalarchitecture.people.evidence.application.domain.Person;
import com.baeldung.patterns.hexagonalarchitecture.people.evidence.application.port.primary.impl.SearchPersonUseCaseImpl;
import com.baeldung.patterns.hexagonalarchitecture.people.evidence.application.port.secondary.PersonRepository;

class SearchPersonUseCaseImplUnitTest {

    private SearchPersonUseCaseImpl searchPersonUseCase;

    private PersonRepository personRepository;

    @BeforeEach
    void setUp() {
        personRepository = Mockito.mock(PersonRepository.class);
        searchPersonUseCase = new SearchPersonUseCaseImpl(personRepository);
    }

    @Test
    void givenPersonExistsInRepository_whenSearchingByName_thenExpectCorrectResult() {
        when(personRepository.findByName("John")).thenReturn(Optional.of(new Person("John", 30)));

        Optional<Person> personOptional = searchPersonUseCase.findByName("John");

        assertThat(personOptional).isPresent();
        Person person = personOptional.get();
        assertThat(person.getName()).isEqualTo("John");
        assertThat(person.getAge()).isEqualTo(30);
    }

    @Test
    void givenPersonDoesNotExistsInRepository_whenSearchingByName_thenExpectCorrectResult() {
        when(personRepository.findByName("John")).thenReturn(Optional.empty());

        Optional<Person> personOptional = searchPersonUseCase.findByName("John");

        assertThat(personOptional).isEmpty();
    }
}