package com.baeldung.jersey.server.response;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class JsonResponse {
    public static void main(String[] args) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("https://api.example.com/user");
        String jsonPayload = "{\"id\":1}";
        Response response = target.request(MediaType.APPLICATION_JSON).post(Entity.entity(jsonPayload, MediaType.APPLICATION_JSON));
        try {
            if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                User user = response.readEntity(User.class);
                System.out.println("User Name: " + user.getName());
            } else {
                System.err.println("Failed to get user data");
            }
        } finally {
            response.close();
            client.close();
        }
    }
}
