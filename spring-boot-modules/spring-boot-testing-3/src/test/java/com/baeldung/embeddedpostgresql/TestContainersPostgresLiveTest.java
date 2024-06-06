package com.baeldung.embeddedpostgresql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

@DataJpaTest
@ExtendWith(TestContainersInitializer.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = TestContainersInitializer.class)
class TestContainersPostgresLiveTest {

    @Autowired
    private PersonRepository repository;

    @Test
    void givenTestcontainersPostgres_whenSavePerson_thenSavedEntityShouldBeReturnedWithExpectedFields() {
        Person person = new Person();
        person.setName("New user");

        Person savedPerson = repository.save(person);
        assertNotNull(savedPerson.getId());
        assertEquals(person.getName(), savedPerson.getName());
    }
}
