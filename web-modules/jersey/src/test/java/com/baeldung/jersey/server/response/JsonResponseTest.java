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

public class JsonResponseTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(JsonResponse.class);
    }

    @Override
    protected void configureClient(ClientConfig config) {
        config.register(JsonResponse.class);
    }

    @Override
    protected TestContainerFactory getTestContainerFactory() {
        return new GrizzlyWebTestContainerFactory();
    }

    @Test
    public void whenUserExists_thenReturnUserName() {
        String jsonPayload = "{\"id\":1}";
        Response response = target("user")
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.entity(jsonPayload, MediaType.APPLICATION_JSON));

        assertEquals(200, response.getStatus());
        String responseBody = response.readEntity(String.class);
        assertEquals("{\"name\":\"John Smith\",\"id\":1}", responseBody);
    }

    @Test
    public void whenUserRequestFails_thenShowError() {
        String jsonPayload = "{\"id\":1}";
        Response response = target("user")
            .request(MediaType.APPLICATION_JSON)
            .post(Entity.entity(jsonPayload, MediaType.APPLICATION_JSON));

        assertEquals(500, response.getStatus());
    }
}