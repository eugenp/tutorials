package com.baeldung.hexagonal.api;

import com.baeldung.hexagonal.api.jmx.GreetingJmxResource;
import com.baeldung.hexagonal.services.GreetingService;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GreetingApiTest {

    private GreetingService greetingService ;

    @Before
    public void before() {
        greetingService = new GreetingService() ;
    }

    @Test
    public void sayHelloJmx() {
        GreetingJmxResource greetingJmxResource = new GreetingJmxResource(greetingService) ;
        executeSayHelloTest(greetingJmxResource);
    }

    @Test
    public void sayHelloRest() {
        GreetingJmxResource greetingJmxResource = new GreetingJmxResource(greetingService) ;
        executeSayHelloTest(greetingJmxResource);
    }

    protected void executeSayHelloTest(GreetingApi greetingApi) {
        assertEquals("Hello, Eugen Paraschiv!  How are you today?", greetingApi.sayHello("Eugen", "Paraschiv")) ;
    }

}
