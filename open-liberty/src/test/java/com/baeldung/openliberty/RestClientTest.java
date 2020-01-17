package com.baeldung.openliberty;

import static org.junit.Assert.assertEquals;

import javax.json.bind.JsonbBuilder;
import javax.ws.rs.ProcessingException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.baeldung.openliberty.person.model.Person;
import com.baeldung.openliberty.rest.consumes.RestConsumer;
import com.baeldung.openliberty.rest.consumes.UriNotFoundException;

public class RestClientTest {

    private static String BASE_URL;
    
    private final String PERSON = "api/person";

    @BeforeClass
    public static void oneTimeSetup() {
        BASE_URL = "http://localhost:9080"; 
    }

    @Test
    public void testSuite() throws ProcessingException, UriNotFoundException {
        this.testJsonBClientBuilder();
        this.testRestClientBuilder();
    }

    public void testJsonBClientBuilder() {
        String url = BASE_URL + "/" + PERSON + "/1";
        String result = RestConsumer.consumeWithJsonb(url);        
        
        Person person = JsonbBuilder.create().fromJson(result, Person.class);
        assert person.getId() == 1;
        assertEquals(person.getUsername(), "normanlewis");
        assertEquals(person.getEmail(), "normanlewis@email.com");
    }
    
    public void testRestClientBuilder() throws ProcessingException, UriNotFoundException {
        String result = RestConsumer.consumeWithRestBuilder(BASE_URL);        
        
        Person person = JsonbBuilder.create().fromJson(result, Person.class);
        assert person.getId() == 1;
        assertEquals(person.getUsername(), "normanlewis");
        assertEquals(person.getEmail(), "normanlewis@email.com");
    }

}
