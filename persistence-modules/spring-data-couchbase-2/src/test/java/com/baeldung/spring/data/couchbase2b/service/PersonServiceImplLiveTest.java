package com.baeldung.spring.data.couchbase2b.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import com.baeldung.spring.data.couchbase.model.Person;
import com.baeldung.spring.data.couchbase2b.MultiBucketLiveTest;
import com.baeldung.spring.data.couchbase2b.MultiBucketCouchbaseConfig;
import org.joda.time.DateTime;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;

public class PersonServiceImplLiveTest extends MultiBucketLiveTest {

    static final String typeField = "_class";
    static final String john = "John";
    static final String smith = "Smith";
    static final String johnSmithId = "person:" + john + ":" + smith;
    static final Person johnSmith = new Person(johnSmithId, john, smith);
    static final JsonObject jsonJohnSmith = JsonObject.empty().put(typeField, Person.class.getName()).put("firstName", john).put("lastName", smith).put("created", DateTime.now().getMillis());

    static final String foo = "Foo";
    static final String bar = "Bar";
    static final String foobarId = "person:" + foo + ":" + bar;
    static final Person foobar = new Person(foobarId, foo, bar);
    static final JsonObject jsonFooBar = JsonObject.empty().put(typeField, Person.class.getName()).put("firstName", foo).put("lastName", bar).put("created", DateTime.now().getMillis());

    @Autowired
    private PersonServiceImpl personService;

    @BeforeClass
    public static void setupBeforeClass() {
        final Cluster cluster = CouchbaseCluster.create(MultiBucketCouchbaseConfig.NODE_LIST);
        final Bucket bucket = cluster.openBucket(MultiBucketCouchbaseConfig.DEFAULT_BUCKET_NAME, MultiBucketCouchbaseConfig.DEFAULT_BUCKET_PASSWORD);
        bucket.upsert(JsonDocument.create(johnSmithId, jsonJohnSmith));
        bucket.upsert(JsonDocument.create(foobarId, jsonFooBar));
        bucket.close();
        cluster.disconnect();
    }

    @Test
    public void whenFindingPersonByJohnSmithId_thenReturnsJohnSmith() {
        final Person actualPerson = personService.findOne(johnSmithId);
        assertNotNull(actualPerson);
        assertNotNull(actualPerson.getCreated());
        assertEquals(johnSmith, actualPerson);
    }

    @Test
    public void whenFindingAllPersons_thenReturnsTwoOrMorePersonsIncludingJohnSmithAndFooBar() {
        final List<Person> resultList = personService.findAll();
        assertNotNull(resultList);
        assertFalse(resultList.isEmpty());
        assertTrue(resultContains(resultList, johnSmith));
        assertTrue(resultContains(resultList, foobar));
        assertTrue(resultList.size() >= 2);
    }

    @Test
    public void whenFindingByFirstNameJohn_thenReturnsOnlyPersonsNamedJohn() {
        final String expectedFirstName = john;
        final List<Person> resultList = personService.findByFirstName(expectedFirstName);
        assertNotNull(resultList);
        assertFalse(resultList.isEmpty());
        assertTrue(allResultsContainExpectedFirstName(resultList, expectedFirstName));
    }

    @Test
    public void whenFindingByLastNameSmith_thenReturnsOnlyPersonsNamedSmith() {
        final String expectedLastName = smith;
        final List<Person> resultList = personService.findByLastName(expectedLastName);
        assertNotNull(resultList);
        assertFalse(resultList.isEmpty());
        assertTrue(allResultsContainExpectedLastName(resultList, expectedLastName));
    }

    private boolean resultContains(List<Person> resultList, Person person) {
        boolean found = false;
        for (final Person p : resultList) {
            if (p.equals(person)) {
                found = true;
                break;
            }
        }
        return found;
    }

    private boolean allResultsContainExpectedFirstName(List<Person> resultList, String firstName) {
        boolean found = false;
        for (final Person p : resultList) {
            if (p.getFirstName().equals(firstName)) {
                found = true;
                break;
            }
        }
        return found;
    }

    private boolean allResultsContainExpectedLastName(List<Person> resultList, String lastName) {
        boolean found = false;
        for (final Person p : resultList) {
            if (p.getLastName().equals(lastName)) {
                found = true;
                break;
            }
        }
        return found;
    }
}
