package com.baeldung.embeddedpostgresql;

import static io.zonky.test.db.AutoConfigureEmbeddedDatabase.DatabaseProvider.ZONKY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;

@DataJpaTest
@AutoConfigureEmbeddedDatabase(provider = ZONKY)
class ZonkyEmbeddedPostgresLiveTest {

    @Autowired
    private PersonRepository repository;

    @Test
    void givenZonkyEmbeddedPostgres_whenSavePerson_thenSavedEntityShouldBeReturnedWithExpectedFields(){
        Person person = new Person();
        person.setName("New user");

        Person savedPerson = repository.save(person);
        assertNotNull(savedPerson.getId());
        assertEquals(person.getName(), savedPerson.getName());
    }
}
