package com.baeldung;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

import java.io.IOException;
import java.net.ServerSocket;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;

public class GetRequestMockServer {

    public static ClientAndServer mockServer;
    public static int serverPort;
    public static String simplePathUrl;
    public static final String SERVER_ADDRESS = "127.0.0.1";
    public static final String SIMPLE_PATH = "/httpclient-simple/api/bars/1";

    @BeforeAll
    static void startServer() throws IOException {
        serverPort = getFreePort();
        System.out.println("Free port " + serverPort);
        mockServer = startClientAndServer(serverPort);

        simplePathUrl = "http://" + SERVER_ADDRESS + ":" + serverPort + SIMPLE_PATH;

        mockGetRequest();
    }

    @AfterAll
    static void stopServer() {
        mockServer.stop();
    }

    private static void mockGetRequest() {

        MockServerClient client = new MockServerClient(SERVER_ADDRESS, serverPort);

        client.when(request().withPath(SIMPLE_PATH)
                .withMethod("GET"))
            .respond(response().withStatusCode(HttpStatus.SC_OK)
                .withBody("{\"status\":\"ok\"}"));
    }

    private static int getFreePort() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(0)) {
            return serverSocket.getLocalPort();
        }
    }

}