package com.baeldung.graphql;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.matchers.Times.exactly;
import static org.mockserver.model.StringBody.exact;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.assertj.core.api.Assertions.*;

class DemoUnitTest {

    private static final String SERVER_ADDRESS = "127.0.0.1";
    public static final String HTTP_GET_POST = "GET";
    public static final String QUERY_PARAMETER = "query";
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
    void given_when_then() throws IOException, URISyntaxException {
        String requestQuery = "{allBooks{title}}";
        String responseJson = "{\"data\":{\"allBooks\":[{\"title\":\"Title 1\"},{\"title\":\"Title 2\"}]}}";
        String path = "/graphql";

        new MockServerClient(SERVER_ADDRESS, serverPort)
          .when(
            request()
              .withMethod(HTTP_GET_POST)
              .withPath(path)
              .withQueryStringParameter(QUERY_PARAMETER, requestQuery),
            exactly(1)
          )
          .respond(
            response()
              .withStatusCode(HttpStatusCode.OK_200.code())
              .withBody(responseJson)
          );

        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet("http://" + SERVER_ADDRESS + ":" + serverPort + path);
        URI uri = new URIBuilder(request.getURI())
          .addParameter(QUERY_PARAMETER, requestQuery)
          .build();
        request.setURI(uri);
        HttpResponse response = client.execute(request);

        assertThat(responseJson).isEqualTo(IOUtils.toString(response.getEntity().getContent(), StandardCharsets.UTF_8.name()));
    }

}
