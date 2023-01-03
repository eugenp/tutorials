package com.baeldung.httpinterface;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.configuration.Configuration;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.List;

import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpStatusCode;
import org.mockserver.model.MediaType;
import org.mockserver.verify.VerificationTimes;
import org.slf4j.event.Level;
import org.springframework.http.HttpMethod;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.matchers.Times.exactly;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BooksServiceMockServerTest {

    private static final String SERVER_ADDRESS = "localhost";
    private static final String PATH = "/books";

    private static int serverPort;
    private static ClientAndServer mockServer;
    private static String serviceUrl;

    @BeforeAll
    static void startServer() throws IOException {
        serverPort = getFreePort();
        serviceUrl = "http://" + SERVER_ADDRESS + ":" + serverPort;

        Configuration config = Configuration.configuration().logLevel(Level.WARN);
        mockServer = startClientAndServer(config, serverPort);

        mockAllBooksRequest();
        mockBookByTitleRequest();
    }

    @AfterAll
    static void stopServer() {
        mockServer.stop();
    }

    @Test
    void givenMockedService_whenAllBooksAreRequested_thenTwoBooksAreReturned() {
        BooksClient booksClient = new BooksClient(serviceUrl);
        BooksService booksService = booksClient.getBooksService();

        List<Book> books = booksService.getBooks();
        assertEquals(2, books.size());

        mockServer.verify(
          HttpRequest.request()
            .withMethod(HttpMethod.GET.name())
            .withPath(PATH),
          VerificationTimes.exactly(1)
        );
    }

    @Test
    void givenMockedService_whenBookByTitleIsRequest_thenCorrectBookIsReturned() {
        BooksClient booksClient = new BooksClient(serviceUrl);
        BooksService booksService = booksClient.getBooksService();

        Book book = booksService.getBook("Book_1");
        assertEquals("Book_1", book.title());

        mockServer.verify(
          HttpRequest.request()
            .withMethod(HttpMethod.GET.name())
            .withPath(PATH + "/Book_1"),
          VerificationTimes.exactly(1)
        );
    }

    private static int getFreePort () throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(0)) {
            return serverSocket.getLocalPort();
        }
    }

    private static void mockBookByTitleRequest() {
        new MockServerClient(SERVER_ADDRESS, serverPort)
          .when(
            request()
              .withPath(PATH),
            exactly(1)
          )
          .respond(
            response()
              .withStatusCode(HttpStatusCode.OK_200.code())
              .withContentType(MediaType.APPLICATION_JSON)
              .withBody("[{\"title\":\"Book_1\",\"author\":\"Author_1\",\"year\":1998},{\"title\":\"Book_2\",\"author\":\"Author_2\",\"year\":1999}]")
          );
    }

    private static void mockAllBooksRequest() {
        new MockServerClient(SERVER_ADDRESS, serverPort)
          .when(
            request()
              .withPath(PATH + "/Book_1"),
            exactly(1)
          )
          .respond(
            response()
              .withStatusCode(HttpStatusCode.OK_200.code())
              .withContentType(MediaType.APPLICATION_JSON)
              .withBody("{\"title\":\"Book_1\",\"author\":\"Author_1\",\"year\":1998}")
          );
    }

}
