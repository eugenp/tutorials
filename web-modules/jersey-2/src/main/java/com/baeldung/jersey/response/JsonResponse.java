package com.baeldung.jersey.response;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonResponse {
    private static final Logger logger = LoggerFactory.getLogger(JsonResponse.class);

    public static void main(String[] args) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("https://api.example.com/user");
        String jsonPayload = "{\"id\":1}";
        Response response = target.request(MediaType.APPLICATION_JSON).post(Entity.entity(jsonPayload, MediaType.APPLICATION_JSON));
        try {
            if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                User user = response.readEntity(User.class);
                logger.info("User Name: " + user.getName());
            } else {
                logger.error("Failed to get user data");
            }
        } finally {
            response.close();
            client.close();
        }
    }
}

class User {
    private int id;
    private String name;

    public User() {}

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}