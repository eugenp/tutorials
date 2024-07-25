package com.baeldung.quarkus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.net.URI;

import jakarta.ws.rs.WebApplicationException;

import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.junit.jupiter.api.Test;

import io.quarkus.rest.client.reactive.ClientBasicAuth;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class GreetingResourceTest {

    @Test
    void testHelloEndpointWithoutCredentials() {

        MyService myService = RestClientBuilder.newBuilder()
            .baseUri(URI.create("http://localhost:8081"))
            .build(MyService.class);

        try {
            myService.hello();
        } catch (Exception e) {
            if (e.getCause() instanceof WebApplicationException webApp) {
                assertTrue(webApp.getResponse()
                    .getStatus() == 401);
                return;
            }
        }

        fail("Should have thrown exception with 401");
    }

    @Test
    void testHelloEndpointWithCredentials() {

        @ClientBasicAuth(username = "john", password = "secret1")
        interface MyService$John extends MyService {
        }

        MyService myService = RestClientBuilder.newBuilder()
            .baseUri(URI.create("http://localhost:8081"))
            .build(MyService$John.class);

        assertEquals("Hello from Quarkus REST", myService.hello());
    }

}