package com.baeldung.jersey.server.response;

import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.grizzly.GrizzlyWebTestContainerFactory;
import org.junit.Test;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.server.ResourceConfig;
import static org.junit.Assert.assertEquals;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.test.spi.TestContainerFactory;

public class GenericRestResponseTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(GenericRestResponse.class);
    }

    @Override
    protected void configureClient(ClientConfig config) {
        config.register(GenericRestResponse.class);
    }

    @Override
    protected TestContainerFactory getTestContainerFactory() {
        return new GrizzlyWebTestContainerFactory();
    }

    @Test
    public void testSuccessfulResponse() {
        String jsonPayload = "{\"name\":\"John Doe\",\"email\":\"john.doe@example.com\"}";
        Response response = target("data")
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.entity(jsonPayload, MediaType.APPLICATION_JSON));

        assertEquals(200, response.getStatus());
        String responseBody = response.readEntity(String.class);
        assertEquals("{\"status\":\"success\"}", responseBody);
    }

    @Test
    public void testFailedResponse() {
        Response response = target("data")
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.entity("", MediaType.APPLICATION_JSON));

        assertEquals(500, response.getStatus());
    }
}