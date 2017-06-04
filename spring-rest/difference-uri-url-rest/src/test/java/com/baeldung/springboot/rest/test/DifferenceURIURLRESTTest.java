package com.baeldung.springboot.rest.test;

import static org.junit.Assert.assertNotNull;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import com.baeldung.springboot.rest.client.Greeting;

public class DifferenceURIURLRESTTest {
    final static String URI_STRING = "http://localhost:8080/difference-uri-url-rest/greetings";
    static RestTemplate restTemplate;
    Greeting greeting;

    @BeforeClass
    public static void setupTest() {
        restTemplate = new RestTemplate();
    }

    @Test
    public void givenRestTenplate_whenIsNotNull_thenSuccess() {
        assertNotNull("Rest Template not null", restTemplate);
    }

    @Test
    public void givenWiredConstructorParam_whenIsNotNull_thenSuccess() {
        greeting = restTemplate.getForObject(URI_STRING, Greeting.class);
        assertNotNull("Greeting class is not null", greeting);
    }

}
