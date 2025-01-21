package com.baeldung.jersey.server.response;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenericRestResponse {
    private static final Logger logger = LoggerFactory.getLogger(GenericRestResponse.class);

    public static void main(String[] args) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("https://api.example.com/data"); // Create a JSON payload
        String jsonPayload = "{\"name\":\"John Doe\",\"email\":\"john.doe@example.com\"}";
        Response response = target.request(MediaType.APPLICATION_JSON).post(Entity.entity(jsonPayload, MediaType.APPLICATION_JSON));
        try {
            if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                String responseBody = response.readEntity(String.class);
                logger.info("Response Body: " + responseBody);
            } else {
                logger.error("Failed to get a successful response");
            }
        } finally {
            response.close();
            client.close();
        }
    }
}
