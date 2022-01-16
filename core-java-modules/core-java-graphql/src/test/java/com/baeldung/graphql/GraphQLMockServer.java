package com.baeldung.graphql;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.HttpStatusCode;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.URISyntaxException;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.matchers.Times.exactly;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

class GraphQLMockServer {

    static final String SERVER_ADDRESS = "127.0.0.1";
    static final String HTTP_GET_POST = "GET";
    static final String QUERY_PARAMETER = "query";
    static final String PATH = "/graphql";
    static int serverPort;

    private static ClientAndServer mockServer;

    @BeforeAll
    static void startServer() throws IOException, URISyntaxException {
        serverPort = getFreePort();
        mockServer = startClientAndServer(serverPort);
        mockAllBooksTitleRequest();
        mockAllBooksTitleAuthorRequest();
    }

    @AfterAll
    static void stopServer() {
        mockServer.stop();
    }

    private static void mockAllBooksTitleAuthorRequest() {
        String requestQuery = "{allBooks{title,author}}";
        String responseJson = "{\"data\":{\"allBooks\":[{\"title\":\"Title 1\",\"author\":\"Author 1\"},{\"title\":\"Title 2\",\"author\":\"Author 2\"}]}}";

        new MockServerClient(SERVER_ADDRESS, serverPort)
          .when(
            request()
              .withMethod(HTTP_GET_POST)
              .withPath(PATH)
              .withQueryStringParameter(QUERY_PARAMETER, requestQuery),
            exactly(1)
          )
          .respond(
            response()
              .withStatusCode(HttpStatusCode.OK_200.code())
              .withBody(responseJson)
          );
    }

    private static void mockAllBooksTitleRequest() {
        String requestQuery = "{allBooks{title}}";
        String responseJson = "{\"data\":{\"allBooks\":[{\"title\":\"Title 1\"},{\"title\":\"Title 2\"}]}}";

        new MockServerClient(SERVER_ADDRESS, serverPort)
          .when(
            request()
              .withMethod(HTTP_GET_POST)
              .withPath(PATH)
              .withQueryStringParameter(QUERY_PARAMETER, requestQuery),
            exactly(1)
          )
          .respond(
            response()
              .withStatusCode(HttpStatusCode.OK_200.code())
              .withBody(responseJson)
          );
    }

    private static int getFreePort () throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(0)) {
            return serverSocket.getLocalPort();
        }
    }

}
