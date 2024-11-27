package com.baeldung.moco;

import com.github.dreamhead.moco.HttpServer;
import com.github.dreamhead.moco.Runner;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static com.github.dreamhead.moco.Moco.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MocoExamplesUnitTest {

    @Test
    public void givenMocoHttpServer_whenClientSendsRequest_thenShouldReturnExpectedResponse() throws Exception {
        HttpServer server = httpServer(12349);
        server.request(by(uri("/test"))).response("Test response");

        Runner runner = Runner.runner(server);
        runner.start();  // Start the server

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:12349/test"))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals("Test response", response.body());

        runner.stop();  // Stop the server after test
    }

    @Test
    public void givenMocoJsonResponse_whenClientSendsRequest_thenShouldReturnJsonResponse() throws Exception {
        HttpServer server = httpServer(12350);
        server.request(by(uri("/api/user")))
                .response(header("Content-Type", "application/json"),
                        json("{\"id\": 1, \"name\": \"Alice\", \"email\": \"alice@example.com\"}"));

        Runner runner = Runner.runner(server);
        runner.start();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:12350/api/user"))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals("{\"id\": 1, \"name\": \"Alice\", \"email\": \"alice@example.com\"}", response.body());

        runner.stop();
    }

    @Test
    public void givenMocoJsonFile_whenClientRequestsUserData_thenShouldReturnCorrectJsonResponse() throws Exception {
        HttpServer server = httpServer(12351);
        server.request(by(uri("/api/user")))
                .response(header("Content-Type", "application/json"), file("src/test/resources/user.json"));

        Runner runner = Runner.runner(server);
        runner.start();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:12351/api/user"))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertTrue(response.body().contains("Ryland Grace"));
        Assertions.assertTrue(response.body().contains("ryland.grace@example.com"));

        runner.stop();
    }

    @Test
    public void givenMocoCustomHttpCode_whenRequestingUnknownEndpoint_thenShouldReturn404NotFound() throws Exception {
        // Custom HTTP code (404 Not Found)
        HttpServer server = httpServer(12352);
        server.request(by(uri("/unknown")))
                .response(status(404), text("Not Found"));

        Runner runner = Runner.runner(server);
        runner.start();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:12352/unknown"))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(404, response.statusCode());
        assertEquals("Not Found", response.body());

        runner.stop();
    }

    @Test
    public void givenMocoPostRequest_whenClientSendsPost_thenShouldReturnUpdatedResourceMessage() throws Exception {
        HttpServer server = httpServer(12353);
        server.post(by(uri("/resource")))
                .response(text("resource updated"));

        Runner runner = Runner.runner(server);
        runner.start();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:12353/resource"))
                .POST(HttpRequest.BodyPublishers.noBody()) // Empty POST body
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals("resource updated", response.body());

        runner.stop();
    }

    @Test
    public void givenMocoJsonPath_whenRequestMatchesJsonPath_thenShouldReturnSpecifiedMessage() throws Exception {
        HttpServer server = httpServer(12354);
        server.request(eq(jsonPath("$.item[*].price"), "0"))
                .response("we have free item");

        Runner runner = Runner.runner(server);
        runner.start();

        HttpClient client = HttpClient.newHttpClient();
        String jsonRequest = "{\"item\": [{\"price\": \"0\"}, {\"price\": \"10\"}]}";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:12354/"))
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals("we have free item", response.body());

        runner.stop();
    }

    @Test
    public void givenMocoSequentialResponses_whenClientMakesMultipleRequests_thenShouldReturnResponsesInSequence() throws Exception {
        // Sequential responses example
        HttpServer server = httpServer(12355);
        server.request(by(uri("/user")))
                .response(seq("Alice", "Bob", "Clyde"));

        Runner runner = Runner.runner(server);
        runner.start();

        HttpClient client = HttpClient.newHttpClient();

        // First request should return "Alice"
        HttpRequest request1 = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:12355/user"))
                .build();
        HttpResponse<String> response1 = client.send(request1, HttpResponse.BodyHandlers.ofString());
        assertEquals("Alice", response1.body());

        // Second request should return "Bob"
        HttpRequest request2 = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:12355/user"))
                .build();
        HttpResponse<String> response2 = client.send(request2, HttpResponse.BodyHandlers.ofString());
        assertEquals("Bob", response2.body());

        // Third request should return "Clyde"
        HttpRequest request3 = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:12355/user"))
                .build();
        HttpResponse<String> response3 = client.send(request3, HttpResponse.BodyHandlers.ofString());
        assertEquals("Clyde", response3.body());

        runner.stop();
    }
}
