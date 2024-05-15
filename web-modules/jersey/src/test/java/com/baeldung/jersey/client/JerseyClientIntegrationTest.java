package com.baeldung.jersey.client;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.baeldung.jersey.server.http.EmbeddedHttpServer;

import jakarta.ws.rs.core.Response;

public class JerseyClientIntegrationTest {

    private static int HTTP_OK = 200;

    private static HttpServer httpServer;

    @BeforeAll
    public static void beforeAllTests() {
        httpServer = EmbeddedHttpServer.startServer(URI.create("http://localhost:8080/jersey"));
    }

    @AfterAll
    public static void afterAllTests() {
        httpServer.stop();
    }

    @Test
    public void givenGreetingResource_whenCallingHelloGreeting_thenHelloReturned() {
        String response = JerseyClient.getHelloGreeting();

        assertEquals("hello", response);
    }

    @Test
    public void givenGreetingResource_whenCallingHiGreeting_thenHiReturned() {
        String response = JerseyClient.getHiGreeting();

        assertEquals("hi", response);
    }

    @Test
    public void givenGreetingResource_whenCallingCustomGreeting_thenCustomGreetingReturned() {
        Response response = JerseyClient.getCustomGreeting();
        
        assertEquals(HTTP_OK, response.getStatus());
    }

}
