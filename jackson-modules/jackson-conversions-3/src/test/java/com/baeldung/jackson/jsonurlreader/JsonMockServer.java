package com.baeldung.jackson.jsonurlreader;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

import java.io.IOException;
import java.net.ServerSocket;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.HttpStatusCode;

import com.baeldung.jackson.jsonurlreader.data.Example;

public class JsonMockServer {
    protected static final String JSON_RESPONSE = "{ \"name\": \"A\", \"n\": 1, \"real\": true }";
    protected static final Example OBJECT_RESPONSE = new Example("A", 1, true);

    protected static String serviceUrl;

    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final String PATH = "/sample-json";

    private static ClientAndServer mockServer;
    private static int serverPort;

    @BeforeAll
    static void startServer() throws IOException {
        serverPort = getFreePort();
        serviceUrl = "http://" + SERVER_ADDRESS + ":" + serverPort + PATH;

        mockServer = startClientAndServer(serverPort);

        mockJsonRequest();
    }

    @AfterAll
    static void stopServer() {
        mockServer.stop();
    }

    @SuppressWarnings("resource")
    private static void mockJsonRequest() {
        new MockServerClient(SERVER_ADDRESS, serverPort)
          .when(request()
            .withPath(PATH)
            .withMethod("GET"))
          .respond(response()
            .withStatusCode(HttpStatusCode.OK_200.code())
            .withBody(JSON_RESPONSE)
        );
    }

    private static int getFreePort() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(0)) {
            return serverSocket.getLocalPort();
        }
    }
}
