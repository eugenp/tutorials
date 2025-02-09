package com.baeldung.activej;

import com.baeldung.activej.config.PersonModule;
import com.baeldung.activej.repository.PersonRepository;
import com.baeldung.activej.service.PersonService;
import io.activej.eventloop.Eventloop;
import io.activej.inject.Injector;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ActiveJTest {

    @Test
    void givenPersonModule_whenGetTheServiceBean_thenAllTheDependenciesShouldBePresent() {
        PersonModule personModule = new PersonModule();

        PersonService personService = Injector.of(personModule).getInstance(PersonService.class);
        assertNotNull(personService);
        PersonRepository personRepository = personService.getPersonRepository();
        assertNotNull(personRepository);
        DataSource dataSource = personRepository.getDataSource();
        assertNotNull(dataSource);
    }

    @Test
    void givenEventloop_whenCallFindAndVerifyPerson_thenExpectedVerificationResultShouldBePresent() {
        PersonModule personModule = new PersonModule();

        PersonService personService = Injector.of(personModule).getInstance(PersonService.class);

        Eventloop eventloop = Eventloop.create();

        personService.findAndVerifyPerson("Good person")
          .whenResult(verifiedPerson -> assertEquals("SUCCESS", verifiedPerson.result()));

        eventloop.run();
    }
}
