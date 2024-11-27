public class RestClientExample {
    public static void main(String[] args) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("https://api.example.com/data"); // Create a JSON payload
        String jsonPayload = "{\"name\":\"John Doe\",\"email\":\"john.doe@example.com\"}";
        Response response = target.request(MediaType.APPLICATION_JSON).post(Entity.entity(jsonPayload, MediaType.APPLICATION_JSON));
        try {
            if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                String responseBody = response.readEntity(String.class);
                System.out.println("Response Body: " + responseBody);
            } else {
                System.err.println("Failed to get a successful response");
            }
        } finally {
            response.close();
            client.close();
        }
    }
}