package com.baeldung.ddlauto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.baeldung.ddlauto.model.Person;
import com.baeldung.ddlauto.repository.PersonRepository;

@DataJpaTest
@ActiveProfiles("ddlauto")
class SpringBootDDLAutoUnitTest {

    @Autowired
    private PersonRepository personRepository;

    @BeforeEach
    void setup() {
        // Clean up the Person table to ensure a fresh start
        personRepository.deleteAll();
    }

    @Test
    void whenSavingPerson_thenPersonIsPersistedCorrectly() {
        // Ensure no person exists before the test
        long countBefore = personRepository.count();
        assertEquals(0, countBefore, "No persons should be present before the test");

        // Creating a new Person entity
        Person person = new Person();
        person.setFirstName("John");
        person.setLastName("Doe");

        // Saving the person using the repository
        Person savedPerson = personRepository.save(person);

        // Assertions to verify the results
        assertNotNull(savedPerson, "Saved person should not be null");
        assertTrue(savedPerson.getId() > 0, "ID should be greater than 0"); // Check if ID was generated
        assertEquals("John", savedPerson.getFirstName(), "First name should be 'John'");
        assertEquals("Doe", savedPerson.getLastName(), "Last name should be 'Doe'");

    }
}
