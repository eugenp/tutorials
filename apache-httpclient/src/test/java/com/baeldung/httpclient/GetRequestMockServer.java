package com.baeldung.httpclient;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.matchers.Times.exactly;
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

    public static final String SERVER_ADDRESS = "127.0.0.1";

    public static final String SECURITY_PATH = "/spring-security-rest-basic-auth/api/foos/1";

    public static final String UPLOAD_PATH = "/spring-mvc-java/stub/multipart";

    @BeforeAll
    static void startServer() throws IOException {
        serverPort = getFreePort();
        System.out.println("Free port " + serverPort);
        mockServer = startClientAndServer(serverPort);
        mockGetRequest();
    }

    @AfterAll
    static void stopServer() {
        mockServer.stop();
    }

    private static void mockGetRequest() {

        MockServerClient client = new MockServerClient(SERVER_ADDRESS, serverPort);

        client.when(
                        request()
                                .withPath(SECURITY_PATH)
                                .withMethod("GET"),
                        exactly(1)
                )
                .respond(
                        response()
                                .withStatusCode(HttpStatus.SC_OK)
                                .withBody("{\"status\":\"ok\"}")
                );

        client.when(
                        request()
                                .withPath(UPLOAD_PATH)
                                .withMethod("POST"),
                        exactly(4)
                )
                .respond(
                        response()
                                .withStatusCode(HttpStatus.SC_OK)
                                .withBody("{\"status\":\"ok\"}")
                                .withHeader("Content-Type", "multipart/form-data")
                );
    }

    private static int getFreePort() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(0)) {
            return serverSocket.getLocalPort();
        }
    }

}
