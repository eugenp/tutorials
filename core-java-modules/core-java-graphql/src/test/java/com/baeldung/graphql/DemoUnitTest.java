package com.baeldung.graphql;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.HttpStatusCode;

import java.io.IOException;
import java.net.ServerSocket;
import java.nio.charset.StandardCharsets;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.matchers.Times.exactly;
import static org.mockserver.model.StringBody.exact;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.assertj.core.api.Assertions.*;

class DemoUnitTest {

    private static final String SERVER_ADDRESS = "127.0.0.1";
    public static final String HTTP_METHOD_POST = "POST";
    private static int serverPort;
    private static ClientAndServer mockServer;

    @BeforeAll
    static void startServer() throws IOException {
        serverPort = getFreePort();
        mockServer = startClientAndServer(serverPort);
    }

    @AfterAll
    static void stopServer() {
        mockServer.stop();
    }

    private static int getFreePort () throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(0)) {
            return serverSocket.getLocalPort();
        }
    }

    @Test
    void given_when_then() throws IOException {
        String requestJson = "{username: 'foo'}";
        String responseJson = "{message: 'ok'}";
        String path = "/test";

        new MockServerClient(SERVER_ADDRESS, serverPort)
          .when(
            request()
              .withMethod(HTTP_METHOD_POST)
              .withPath(path)
              .withBody(exact(requestJson)),
            exactly(1)
          )
          .respond(
            response()
              .withStatusCode(HttpStatusCode.OK_200.code())
              .withBody(responseJson)
          );

        HttpClient client = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost("http://" + SERVER_ADDRESS + ":" + serverPort + path);
        StringEntity entity = new StringEntity(requestJson);
        request.setEntity(entity);
        HttpResponse response = client.execute(request);

        assertThat(responseJson).isEqualTo(IOUtils.toString(response.getEntity().getContent(), StandardCharsets.UTF_8.name()));
    }

}
