package com.baeldung.jersey.response;

import com.baeldung.jersey.model.User;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.slf4j.Logger;

public class JsonResponse {
    private final Logger logger;
    private final Client client;
    private final String baseUrl;

    public JsonResponse(Client client, Logger logger, String baseUrl) {
        this.client = client;
        this.logger = logger;
        this.baseUrl = baseUrl;
    }

    public User fetchUserData(int userId) {
        WebTarget target = client.target(baseUrl);
        String jsonPayload = String.format("{\"id\":%d}", userId);

        try (Response response = target.request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(jsonPayload, MediaType.APPLICATION_JSON))) {

            if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                return response.readEntity(User.class);
            } else {
                logger.error("Failed to get user data. Status: {}", response.getStatus());
                return null;
            }
        } catch (Exception e) {
            logger.error("Error processing user data", e);
            return null;
        }
    }
}

