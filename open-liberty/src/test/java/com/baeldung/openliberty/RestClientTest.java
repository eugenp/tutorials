package com.baeldung.openliberty;

import static org.junit.Assert.assertEquals;

import javax.json.bind.JsonbBuilder;

import org.junit.BeforeClass;
import org.junit.Test;

import com.baeldung.openliberty.person.model.Person;
import com.baeldung.openliberty.rest.consumes.RestConsumer;

public class RestClientTest {

    private static String BASE_URL;

    private final String PERSON = "api/person";

    @BeforeClass
    public static void oneTimeSetup() {
        BASE_URL = "http://localhost:9080"; 
    }

    @Test
    public void whenConsumeWithJsonb_thenGetPerson() {
        String url = BASE_URL + "/" + PERSON + "/1";
        String result = RestConsumer.consumeWithJsonb(url);        

        Person person = JsonbBuilder.create().fromJson(result, Person.class);
        assert person.getId() == 1;
        assertEquals(person.getUsername(), "normanlewis");
        assertEquals(person.getEmail(), "normanlewis@email.com");
    }

    @Test
    public void whenConsumeWithRestBuilder_thenGetPerson() {
        String result = RestConsumer.consumeWithRestBuilder(BASE_URL);        

        Person person = JsonbBuilder.create().fromJson(result, Person.class);
        assert person.getId() == 1;
        assertEquals(person.getUsername(), "normanlewis");
        assertEquals(person.getEmail(), "normanlewis@email.com");
    }

}
