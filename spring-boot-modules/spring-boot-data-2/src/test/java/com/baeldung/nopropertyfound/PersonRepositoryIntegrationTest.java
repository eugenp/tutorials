package com.baeldung.nopropertyfound;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.baeldung.nopropertyfound.model.Person;
import com.baeldung.nopropertyfound.repository.PersonRepository;

@DataJpaTest
class PersonRepositoryIntegrationTest {

    @Autowired
    private PersonRepository personRepository;

    @Test
    void givenQueryMethod_whenUsingValidProperty_thenCorrect() {

        Person person = personRepository.findByFirstName("Azhrioun");

        assertNotNull(person);
        assertEquals("Abderrahim", person.getLastName());
    }

}
