package com.baeldung.jersey.server;

import static org.junit.Assert.assertEquals;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class GreetingsResourceIntegrationTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(Greetings.class);
    }

    @Test
    public void givenGetHiGreeting_whenCorrectRequest_thenResponseIsOkAndContainsHi() {
        Response response = target("/greetings/hi").request()
            .get();

        assertEquals("Http Response should be 200: ", Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("Http Content-Type should be: ", MediaType.TEXT_HTML, response.getHeaderString(HttpHeaders.CONTENT_TYPE));

        String content = response.readEntity(String.class);
        assertEquals("Content of ressponse is: ", "hi", content);
    }
}
