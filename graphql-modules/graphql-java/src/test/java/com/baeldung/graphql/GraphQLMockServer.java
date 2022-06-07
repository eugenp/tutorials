package com.baeldung.graphql;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.mockserver.client.MockServerClient;
import org.mockserver.configuration.Configuration;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.HttpStatusCode;
import org.slf4j.event.Level;

import java.io.IOException;
import java.net.ServerSocket;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.matchers.Times.exactly;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

public class GraphQLMockServer {

    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final String PATH = "/graphql";

    public static String serviceUrl;

    private static ClientAndServer mockServer;
    private static int serverPort;

    @BeforeAll
    static void startServer() throws IOException {
        serverPort = getFreePort();
        serviceUrl = "http://" + SERVER_ADDRESS + ":" + serverPort + PATH;

        Configuration config = Configuration.configuration().logLevel(Level.WARN);
        mockServer = startClientAndServer(config, serverPort);

        mockAllBooksTitleRequest();
        mockAllBooksTitleAuthorRequest();
    }

    @AfterAll
    static void stopServer() {
        mockServer.stop();
    }

    private static void mockAllBooksTitleAuthorRequest() {
        String requestQuery = "{allBooks{title,author{name,surname}}}";
        String responseJson = "{\"data\":{\"allBooks\":[{\"title\":\"Title 1\",\"author\":{\"name\":\"Pero\",\"surname\":\"Peric\"}},{\"title\":\"Title 2\",\"author\":{\"name\":\"Marko\",\"surname\":\"Maric\"}}]}}";

        new MockServerClient(SERVER_ADDRESS, serverPort)
          .when(
            request()
              .withPath(PATH)
              .withQueryStringParameter("query", requestQuery),
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
              .withPath(PATH)
              .withQueryStringParameter("query", requestQuery),
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
