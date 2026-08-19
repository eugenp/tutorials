package com.baeldung.microcks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import com.sun.net.httpserver.HttpServer;

import io.github.microcks.testcontainers.MicrocksContainer;
import io.github.microcks.testcontainers.model.TestRequest;
import io.github.microcks.testcontainers.model.TestResult;
import io.github.microcks.testcontainers.model.TestRunnerType;

/**
 * Starts a real Microcks instance via Testcontainers, imports the API Pastries OpenAPI
 * contract, and uses it both as a live mock and to contract-test a small stand-in
 * implementation of the API.
 */
class MicrocksLiveTest {

    private static final MicrocksContainer microcks = new MicrocksContainer(
      DockerImageName.parse("quay.io/microcks/microcks-uber:1.14.0"))
      .withMainArtifacts("apipastries-openapi.yaml");

    private static HttpServer pastryServer;
    private static int pastryServerPort;

    private final HttpClient client = HttpClient.newHttpClient();

    @BeforeAll
    static void startContainers() throws IOException {
        pastryServer = HttpServer.create(new InetSocketAddress(0), 0);
        pastryServer.createContext("/pastries/", exchange -> {
            String name = exchange.getRequestURI()
              .getPath()
              .substring("/pastries/".length());
            String body = switch (name) {
                case "Millefeuille" ->
                    "{\"name\":\"Millefeuille\",\"description\":\"Delicieux Millefeuille pas calorique du tout\",\"size\":\"L\",\"price\":4.4,\"status\":\"available\"}";
                case "Eclair Cafe" ->
                    "{\"name\":\"Eclair Cafe\",\"description\":\"Delicieux Eclair au Cafe pas calorique du tout\",\"size\":\"M\",\"price\":2.5,\"status\":\"available\"}";
                default -> null;
            };

            if (body == null) {
                exchange.sendResponseHeaders(404, -1);
                exchange.close();
                return;
            }

            exchange.getResponseHeaders()
              .add("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, body.getBytes(StandardCharsets.UTF_8).length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(body.getBytes(StandardCharsets.UTF_8));
            }
        });
        pastryServer.start();
        pastryServerPort = pastryServer.getAddress()
          .getPort();

        // Must run before any container starts, so the container picks up the host.testcontainers.internal route.
        Testcontainers.exposeHostPorts(pastryServerPort);

        microcks.start();
    }

    @AfterAll
    static void stopContainers() {
        pastryServer.stop(0);
        microcks.stop();
    }

    @Test
    void whenCallingRestMockEndpoint_thenMicrocksReturnsExampleFromContract() throws Exception {
        String baseApiUrl = microcks.getRestMockEndpoint("API Pastries", "0.0.1");

        HttpRequest request = HttpRequest.newBuilder(URI.create((baseApiUrl + "/pastries/Millefeuille").replace(" ", "%20")))
          .GET()
          .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
        assertTrue(response.body()
          .contains("Millefeuille"));
    }

    @Test
    void givenMockEndpointWasCalled_whenCheckingInvocations_thenMicrocksReportsTheCall() throws Exception {
        String baseApiUrl = microcks.getRestMockEndpoint("API Pastries", "0.0.1");
        HttpRequest request = HttpRequest.newBuilder(URI.create((baseApiUrl + "/pastries/Millefeuille").replace(" ", "%20")))
          .GET()
          .build();
        client.send(request, HttpResponse.BodyHandlers.ofString());

        Long invocations = microcks.getServiceInvocationsCount("API Pastries", "0.0.1");

        assertTrue(invocations >= 1);
    }

    @Test
    void givenConformantImplementation_whenRunningContractTest_thenTestSucceeds() throws Exception {
        TestRequest testRequest = new TestRequest.Builder()
          .serviceId("API Pastries:0.0.1")
          .runnerType(TestRunnerType.OPEN_API_SCHEMA.name())
          .testEndpoint("http://host.testcontainers.internal:" + pastryServerPort)
          .filteredOperations(List.of("GET /pastries/{name}"))
          .timeout(Duration.ofSeconds(5))
          .build();

        TestResult testResult = microcks.testEndpoint(testRequest);

        assertTrue(testResult.isSuccess());
    }
}
