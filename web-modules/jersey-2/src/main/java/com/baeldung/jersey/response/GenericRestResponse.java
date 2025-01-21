package com.baeldung.jersey.response;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenericRestResponse {
    private final Logger logger;
    private final Client client;

    public GenericRestResponse(Client client, Logger logger) {
        this.client = client;
        this.logger = logger;
    }

    public GenericRestResponse() {
        this(ClientBuilder.newClient(), LoggerFactory.getLogger(GenericRestResponse.class));
    }

    public void sendRequest(String url, String jsonPayload) {
        WebTarget target = client.target(url);
        Response response = target.request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(jsonPayload, MediaType.APPLICATION_JSON));

        try {
            if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                String responseBody = response.readEntity(String.class);
                logger.info("Response Body: " + responseBody);
            } else {
                logger.error("Failed to get a successful response");
            }
        } catch (RuntimeException e) {
            logger.error("Error processing response", e);
        } finally {
            response.close();
            client.close();
        }
    }
}