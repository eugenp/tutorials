package com.baeldung.couchbase.async.person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baeldung.couchbase.async.AsyncIntegrationTest;
import com.baeldung.couchbase.async.service.BucketService;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.document.JsonDocument;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersonCrudServiceIntegrationTestConfig.class})
public class PersonCrudServiceLiveTest extends AsyncIntegrationTest {

    @Autowired
    private PersonCrudService personService;

    @Autowired
    @Qualifier("TutorialBucketService")
    private BucketService bucketService;

    @Autowired
    private PersonDocumentConverter converter;

    private Bucket bucket;

    @Before
    public void init() {
        bucket = bucketService.getBucket();
    }

    @Test
    public final void givenRandomPerson_whenCreate_thenPersonPersisted() {
        // create person
        Person person = randomPerson();
        personService.create(person);

        // check results
        assertNotNull(person.getId());
        assertNotNull(bucket.get(person.getId()));

        // cleanup
        bucket.remove(person.getId());
    }

    @Test
    public final void givenId_whenRead_thenReturnsPerson() {
        // create and insert person document
        String id = insertRandomPersonDocument().id();

        // read person and check results
        assertNotNull(personService.read(id));

        // cleanup
        bucket.remove(id);
    }

    @Test
    public final void givenNewHometown_whenUpdate_thenNewHometownPersisted() {
        // create and insert person document
        JsonDocument doc = insertRandomPersonDocument();

        // update person
        Person expected = converter.fromDocument(doc);
        String updatedHomeTown = RandomStringUtils.randomAlphabetic(12);
        expected.setHomeTown(updatedHomeTown);
        personService.update(expected);

        // check results
        JsonDocument actual = bucket.get(expected.getId());
        assertNotNull(actual);
        assertNotNull(actual.content());
        assertEquals(expected.getHomeTown(), actual.content().getString("homeTown"));

        // cleanup
        bucket.remove(expected.getId());
    }

    @Test
    public final void givenRandomPerson_whenDelete_thenPersonNotInBucket() {
        // create and insert person document
        String id = insertRandomPersonDocument().id();

        // delete person and check results
        personService.delete(id);
        assertNull(bucket.get(id));
    }

    @Test
    public final void givenIds_whenReadBulk_thenReturnsOnlyPersonsWithMatchingIds() {
        List<String> ids = new ArrayList<>();

        // add some person documents
        for (int i = 0; i < 5; i++) {
            ids.add(insertRandomPersonDocument().id());
        }

        // perform bulk read
        List<Person> persons = personService.readBulk(ids);

        // check results
        for (Person person : persons) {
            assertTrue(ids.contains(person.getId()));
        }

        // cleanup
        for (String id : ids) {
            bucket.remove(id);
        }
    }

    @Test
    public final void givenPersons_whenInsertBulk_thenPersonsAreInserted() {

        // create some persons
        List<Person> persons = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            persons.add(randomPerson());
        }

        // perform bulk insert
        personService.createBulk(persons);

        // check results
        for (Person person : persons) {
            assertNotNull(bucket.get(person.getId()));
        }

        // cleanup
        for (Person person : persons) {
            bucket.remove(person.getId());
        }
    }

    @Test
    public final void givenPersons_whenUpdateBulk_thenPersonsAreUpdated() {

        List<String> ids = new ArrayList<>();

        // add some person documents
        for (int i = 0; i < 5; i++) {
            ids.add(insertRandomPersonDocument().id());
        }

        // load persons from Couchbase
        List<Person> persons = new ArrayList<>();
        for (String id : ids) {
            persons.add(converter.fromDocument(bucket.get(id)));
        }

        // modify persons
        for (Person person : persons) {
            person.setHomeTown(RandomStringUtils.randomAlphabetic(10));
        }

        // perform bulk update
        personService.updateBulk(persons);

        // check results
        for (Person person : persons) {
            JsonDocument doc = bucket.get(person.getId());
            assertEquals(person.getName(), doc.content().getString("name"));
            assertEquals(person.getHomeTown(), doc.content().getString("homeTown"));
        }

        // cleanup
        for (String id : ids) {
            bucket.remove(id);
        }
    }

    @Test
    public void givenIds_whenDeleteBulk_thenPersonsAreDeleted() {

        List<String> ids = new ArrayList<>();

        // add some person documents
        for (int i = 0; i < 5; i++) {
            ids.add(insertRandomPersonDocument().id());
        }

        // perform bulk delete
        personService.deleteBulk(ids);

        // check results
        for (String id : ids) {
            assertNull(bucket.get(id));
        }

    }

    private JsonDocument insertRandomPersonDocument() {
        Person expected = randomPersonWithId();
        JsonDocument doc = converter.toDocument(expected);
        return bucket.insert(doc);
    }

    private Person randomPerson() {
        return Person.Builder.newInstance().name(RandomStringUtils.randomAlphabetic(10)).homeTown(RandomStringUtils.randomAlphabetic(10)).build();
    }

    private Person randomPersonWithId() {
        return Person.Builder.newInstance().id(UUID.randomUUID().toString()).name(RandomStringUtils.randomAlphabetic(10)).homeTown(RandomStringUtils.randomAlphabetic(10)).build();
    }
}
