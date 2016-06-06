package org.baeldung.spring.data.couchbase.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.baeldung.spring.data.couchbase.IntegrationTest;
import org.baeldung.spring.data.couchbase.MyCouchbaseConfig;
import org.baeldung.spring.data.couchbase.model.Person;
import org.joda.time.DateTime;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;

public class PersonRepositoryServiceTest extends IntegrationTest {

    private static final String typeField = "_class";
    private static final String john = "John";
    private static final String smith = "Smith";
    private static final String johnSmithId = "person:" + john + ":" + smith;
    private static final Person johnSmith = new Person(johnSmithId, john, smith);
    private static final JsonObject jsonJohnSmith = JsonObject.empty()
            .put(typeField, Person.class.getName())
            .put("firstName", john)
            .put("lastName", smith)
            .put("created", DateTime.now().getMillis());

    private static final String foo = "Foo";
    private static final String bar = "Bar";
    private static final String foobarId = "person:" + foo + ":" + bar;
    private static final Person foobar = new Person(foobarId, foo, bar);
    private static final JsonObject jsonFooBar = JsonObject.empty()
            .put(typeField, Person.class.getName())
            .put("firstName", foo)
            .put("lastName", bar)
            .put("created", DateTime.now().getMillis());
    
    @Autowired
    @Qualifier("PersonRepositoryService")
    private PersonService personService;
    
    @BeforeClass
    public static void setupBeforeClass() {
        Cluster cluster = CouchbaseCluster.create(MyCouchbaseConfig.NODE_LIST);
        Bucket bucket = cluster.openBucket(MyCouchbaseConfig.DEFAULT_BUCKET_NAME, MyCouchbaseConfig.DEFAULT_BUCKET_PASSWORD);
        bucket.upsert(JsonDocument.create(johnSmithId, jsonJohnSmith));
        bucket.upsert(JsonDocument.create(foobarId, jsonFooBar));
        bucket.close();
        cluster.disconnect();
    }

    @Test
    public void whenFindingPersonByJohnSmithId_thenReturnsJohnSmith() {
        Person actualPerson = personService.findOne(johnSmithId);
        assertNotNull(actualPerson);
        assertNotNull(actualPerson.getCreated());
        assertEquals(johnSmith, actualPerson);
    }

    @Test
    public void whenFindingAllPersons_thenReturnsTwoOrMorePersonsIncludingJohnSmithAndFooBar() {
        List<Person> resultList = personService.findAll();
        assertNotNull(resultList);
        assertFalse(resultList.isEmpty());
        assertTrue(resultContains(resultList, johnSmith));
        assertTrue(resultContains(resultList, foobar));
        assertTrue(resultList.size() >= 2);
    }

    @Test
    public void whenFindingByFirstNameJohn_thenReturnsOnlyPersonsNamedJohn() {
        String expectedFirstName = john;
        List<Person> resultList = personService.findByFirstName(expectedFirstName);
        assertNotNull(resultList);
        assertFalse(resultList.isEmpty());
        assertTrue(allResultsContainExpectedFirstName(resultList, expectedFirstName));
    }

    @Test
    public void whenFindingByLastNameSmith_thenReturnsOnlyPersonsNamedSmith() {
        String expectedLastName = smith;
        List<Person> resultList = personService.findByLastName(expectedLastName);
        assertNotNull(resultList);
        assertFalse(resultList.isEmpty());
        assertTrue(allResultsContainExpectedLastName(resultList, expectedLastName));
    }
    
    private boolean resultContains(List<Person> resultList, Person person) {
        boolean found = false;
        for(Person p : resultList) {
            if(p.equals(person)) {
                found = true;
                break;
            }
        }
        return found;
    }

    private boolean allResultsContainExpectedFirstName(List<Person> resultList, String firstName) {
        boolean found = false;
        for(Person p : resultList) {
            if(p.getFirstName().equals(firstName)) {
                found = true;
                break;
            }
        }
        return found;
    }

    private boolean allResultsContainExpectedLastName(List<Person> resultList, String lastName) {
        boolean found = false;
        for(Person p : resultList) {
            if(p.getLastName().equals(lastName)) {
                found = true;
                break;
            }
        }
        return found;
    }
}
