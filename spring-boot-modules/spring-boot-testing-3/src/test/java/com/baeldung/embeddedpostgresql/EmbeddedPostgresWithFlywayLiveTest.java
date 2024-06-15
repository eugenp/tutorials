package com.baeldung.embeddedpostgresql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import com.baeldung.embeddedpostgresql.EmbeddedPostgresConfiguration.EmbeddedPostgresExtension;

@DataJpaTest(properties = { "spring.jpa.hibernate.ddl-auto=none" })
@ExtendWith(EmbeddedPostgresExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = { EmbeddedPostgresWithFlywayConfiguration.class })
class EmbeddedPostgresWithFlywayLiveTest {

    @Autowired
    private PersonRepository repository;

    @Test
    void givenEmbeddedPostgres_whenSavePerson_thenSavedEntityShouldBeReturnedWithExpectedFields() {
        Person person = new Person();
        person.setName("New user");

        Person savedPerson = repository.save(person);
        assertNotNull(savedPerson.getId());
        assertEquals(person.getName(), savedPerson.getName());

        List<Person> allPersons = repository.findAll();
        Assertions.assertThat(allPersons)
            .contains(person);
    }
}