package com.baeldung.httpclient.httpclient;

import org.apache.hc.core5.http.HttpStatus;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.URISyntaxException;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.matchers.Times.exactly;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

public class GetRequestMockServer {

    public static ClientAndServer mockServer;
    public static String serviceOneUrl;
    public static String serviceTwoUrl;

    private static int serverPort;

    public static final String SERVER_ADDRESS = "127.0.0.1";
    public static final String PATH_ONE = "/test1";
    public static final String PATH_TWO = "/test2";
    public static final String METHOD = "GET";

    @BeforeAll
    static void startServer() throws IOException, URISyntaxException {
        serverPort = getFreePort();
        serviceOneUrl = "http://" + SERVER_ADDRESS + ":" + serverPort + PATH_ONE;
        serviceTwoUrl = "http://" + SERVER_ADDRESS + ":" + serverPort + PATH_TWO;
        mockServer = startClientAndServer(serverPort);
        mockGetRequest();
    }

    @AfterAll
    static void stopServer() {
        mockServer.stop();
    }

    private static void mockGetRequest() {
        new MockServerClient(SERVER_ADDRESS, serverPort)
          .when(
            request()
              .withPath(PATH_ONE)
              .withMethod(METHOD),
            exactly(5)
          )
          .respond(
            response()
              .withStatusCode(HttpStatus.SC_OK)
              .withBody("{\"status\":\"ok\"}")
          );
        new MockServerClient(SERVER_ADDRESS, serverPort)
          .when(
            request()
              .withPath(PATH_TWO)
              .withMethod(METHOD),
            exactly(1)
          )
          .respond(
            response()
              .withStatusCode(HttpStatus.SC_OK)
              .withBody("{\"status\":\"ok\"}")
          );
    }

    private static int getFreePort () throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(0)) {
            return serverSocket.getLocalPort();
        }
    }

}
