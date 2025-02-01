package com.baeldung.quarkus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.net.URI;

import jakarta.ws.rs.WebApplicationException;

import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class ClientBasicAuthUnitTest {

    @Test
    void whenNoCredentialsProvided_then401StatusCode() {

        MyService myService = RestClientBuilder.newBuilder()
            .baseUri(URI.create("http://localhost:8081"))
            .build(MyService.class);

        try {
            myService.hello();
        } catch (Exception e) {
            if (e.getCause() instanceof WebApplicationException webApp) {
                assertTrue(webApp.getResponse().getStatus() == 401);
                return;
            }
        }

        fail("Should have thrown exception with 401");
    }

    @Test
    void whenRightCredentialsProvided_thenResponseReceived() {
        MyService myService = RestClientBuilder.newBuilder()
            .baseUri(URI.create("http://localhost:8081"))
            .build(MyServiceJohnRightCredentials.class);

        assertEquals("Hello from Quarkus REST", myService.hello());
    }

    @Test
    void whenWrongCredentinalsProvided_then401StatusCode() {
        MyService myService = RestClientBuilder.newBuilder()
            .baseUri(URI.create("http://localhost:8081"))
            .build(MyServiceJohnWrongCredentials.class);

        try {
            myService.hello();
        } catch (Exception e) {
            if (e.getCause() instanceof WebApplicationException webApp) {
                assertTrue(webApp.getResponse().getStatus() == 401);
                return;
            }
        }

        fail("Should have thrown exception with 401");
    }

}