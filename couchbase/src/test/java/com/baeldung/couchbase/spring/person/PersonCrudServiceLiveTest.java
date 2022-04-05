package com.baeldung.couchbase.spring.person;

import static org.junit.Assert.*;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.baeldung.couchbase.spring.IntegrationTest;

public class PersonCrudServiceLiveTest extends IntegrationTest {

    private static final String CLARK_KENT = "Clark Kent";
    private static final String SMALLVILLE = "Smallville";
    private static final String CLARK_KENT_ID = "Person:ClarkKent";

    private Person clarkKent;

    @Autowired
    private PersonCrudService personService;

    @PostConstruct
    private void init() {
        clarkKent = personService.read(CLARK_KENT_ID);
        if (clarkKent == null) {
            clarkKent = buildClarkKent();
            personService.create(clarkKent);
        }
    }

    @Test
    public final void givenRandomPerson_whenCreate_thenPersonPersisted() {
        Person person = randomPerson();
        personService.create(person);
        String id = person.getId();
        assertNotNull(personService.read(id));
    }

    @Test
    public final void givenClarkKentId_whenRead_thenReturnsClarkKent() {
        Person person = personService.read(CLARK_KENT_ID);
        assertNotNull(person);
    }

    @Test
    public final void givenNewHometown_whenUpdate_thenNewHometownPersisted() {
        Person expected = randomPerson();
        personService.create(expected);
        String updatedHomeTown = RandomStringUtils.randomAlphabetic(12);
        expected.setHomeTown(updatedHomeTown);
        personService.update(expected);
        Person actual = personService.read(expected.getId());
        assertNotNull(actual);
        assertEquals(expected.getHomeTown(), actual.getHomeTown());
    }

    @Test
    public final void givenRandomPerson_whenDelete_thenPersonNotInBucket() {
        Person person = randomPerson();
        personService.create(person);
        String id = person.getId();
        personService.delete(id);
        assertNull(personService.read(id));
    }

    private Person buildClarkKent() {
        return Person.Builder.newInstance().id(CLARK_KENT_ID).name(CLARK_KENT).homeTown(SMALLVILLE).build();
    }

    private Person randomPerson() {
        return Person.Builder.newInstance().name(RandomStringUtils.randomAlphabetic(10)).homeTown(RandomStringUtils.randomAlphabetic(10)).build();
    }
}
