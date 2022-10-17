package com.baeldung.openliberty;

import static org.junit.Assert.assertEquals;

import javax.json.bind.JsonbBuilder;

import org.junit.BeforeClass;
import org.junit.Test;

import com.baeldung.openliberty.person.model.Person;
import com.baeldung.openliberty.rest.consumes.RestConsumer;

public class RestClientLiveTest {

    private static String BASE_URL;

    private final String API_PERSON = "api/persons";

    @BeforeClass
    public static void oneTimeSetup() {
        BASE_URL = "http://localhost:9080/"; 
    }
    
    @Test
    public void testSuite() {
        //run the test only when liberty server is started
        //this.whenConsumeWithJsonb_thenGetPerson();
    }

    public void whenConsumeWithJsonb_thenGetPerson() {
        String url = BASE_URL + API_PERSON + "/1";
        String result = RestConsumer.consumeWithJsonb(url);        

        Person person = JsonbBuilder.create().fromJson(result, Person.class);
        assertEquals(1, person.getId());
        assertEquals("normanlewis", person.getUsername());
        assertEquals("normanlewis@email.com", person.getEmail());
    }

}
