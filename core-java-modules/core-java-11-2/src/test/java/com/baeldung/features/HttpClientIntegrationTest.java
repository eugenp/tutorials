package com.baeldung.features;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.HttpStatusCode;
import org.mockserver.socket.PortFactory;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;

class HttpClientIntegrationTest {

    private static ClientAndServer mockServer;
    private static int port;

    @BeforeAll
    static void startServer() {
        port = PortFactory.findFreePort();
        mockServer = startClientAndServer(port);
        mockServer.when(new org.mockserver.model.HttpRequest().withMethod("GET"))
          .respond(new org.mockserver.model.HttpResponse()
            .withStatusCode(HttpStatusCode.OK_200.code())
            .withBody("Hello from the server!"));
    }

    @AfterAll
    static void stopServer() {
        mockServer.stop();
    }

    @Test
    void givenSampleHttpRequest_whenRequestIsSent_thenServerResponseIsReceived() throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newBuilder()
          .version(HttpClient.Version.HTTP_2)
          .connectTimeout(Duration.ofSeconds(20))
          .build();
        HttpRequest httpRequest = HttpRequest.newBuilder()
          .GET()
          .uri(URI.create("http://localhost:" + port))
          .build();
        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        assertThat(httpResponse.body()).isEqualTo("Hello from the server!");
    }

}
