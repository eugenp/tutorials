package com.baeldung.httpclient;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.matchers.Times.exactly;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

import java.io.IOException;
import java.net.ServerSocket;

import org.apache.hc.core5.http.HttpStatus;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;

public class GetRequestMockServer {

    public static ClientAndServer mockServer;

    public static int serverPort;

    public static String serviceOneUrl;
    public static String serviceTwoUrl;

    public static final String SERVER_ADDRESS = "127.0.0.1";

    public static final String PATH_ONE = "/path_one";
    public static final String PATH_TWO = "/path-two";
    public static final String METHOD = "GET";

    public static final String UPLOAD_PATH = "/spring-mvc-java/stub/multipart";

    @BeforeAll
    static void startServer() throws IOException {
        serverPort = getFreePort();
        System.out.println("Free port " + serverPort);
        mockServer = startClientAndServer(serverPort);

        serviceOneUrl = "http://" + SERVER_ADDRESS + ":" + serverPort + PATH_ONE;
        serviceTwoUrl = "http://" + SERVER_ADDRESS + ":" + serverPort + PATH_TWO;
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

        client
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
        client
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

    private static int getFreePort() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(0)) {
            return serverSocket.getLocalPort();
        }
    }

}
