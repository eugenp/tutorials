package com.baeldung.httpinterface;

import org.apache.http.HttpStatus;
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
import org.mockserver.model.MediaType;
import org.mockserver.verify.VerificationTimes;
import org.slf4j.event.Level;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertThrows;
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
        mockBookByIdRequest();
        mockSaveBookRequest();
        mockDeleteBookRequest();
    }

    @AfterAll
    static void stopServer() {
        mockServer.stop();
    }

    @Test
    void givenMockedGetResponse_whenGetBooksServiceMethodIsCalled_thenTwoBooksAreReturned() {
        BooksClient booksClient = new BooksClient(WebClient.builder().baseUrl(serviceUrl).build());
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
    void givenMockedGetResponse_whenGetExistingBookServiceMethodIsCalled_thenCorrectBookIsReturned() {
        BooksClient booksClient = new BooksClient(WebClient.builder().baseUrl(serviceUrl).build());
        BooksService booksService = booksClient.getBooksService();

        Book book = booksService.getBook(1);
        assertEquals("Book_1", book.title());

        mockServer.verify(
          HttpRequest.request()
            .withMethod(HttpMethod.GET.name())
            .withPath(PATH + "/1"),
          VerificationTimes.exactly(1)
        );
    }

    @Test
    void givenMockedGetResponse_whenGetNonExistingBookServiceMethodIsCalled_thenCorrectBookIsReturned() {
        BooksClient booksClient = new BooksClient(WebClient.builder().baseUrl(serviceUrl).build());
        BooksService booksService = booksClient.getBooksService();

        assertThrows(WebClientResponseException.class, () -> booksService.getBook(9));
    }

    @Test
    void givenCustomErrorHandlerIsSet_whenGetNonExistingBookServiceMethodIsCalled_thenCustomExceptionIsThrown() {
        BooksClient booksClient = new BooksClient(WebClient.builder()
          .defaultStatusHandler(HttpStatusCode::isError, resp ->
            Mono.just(new MyServiceException("Custom exception")))
          .baseUrl(serviceUrl)
          .build());

        BooksService booksService = booksClient.getBooksService();
        assertThrows(MyServiceException.class, () -> booksService.getBook(9));
    }

    @Test
    void givenMockedPostResponse_whenSaveBookServiceMethodIsCalled_thenCorrectBookIsReturned() {
        BooksClient booksClient = new BooksClient(WebClient.builder().baseUrl(serviceUrl).build());
        BooksService booksService = booksClient.getBooksService();

        Book book = booksService.saveBook(new Book(3, "Book_3", "Author_3", 2000));
        assertEquals("Book_3", book.title());

        mockServer.verify(
          HttpRequest.request()
            .withMethod(HttpMethod.POST.name())
            .withPath(PATH),
          VerificationTimes.exactly(1)
        );
    }

    @Test
    void givenMockedDeleteResponse_whenDeleteBookServiceMethodIsCalled_thenCorrectCodeIsReturned() {
        BooksClient booksClient = new BooksClient(WebClient.builder().baseUrl(serviceUrl).build());
        BooksService booksService = booksClient.getBooksService();

        ResponseEntity<Void> response = booksService.deleteBook(3);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());

        mockServer.verify(
          HttpRequest.request()
            .withMethod(HttpMethod.DELETE.name())
            .withPath(PATH + "/3"),
          VerificationTimes.exactly(1)
        );
    }

    private static int getFreePort () throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(0)) {
            return serverSocket.getLocalPort();
        }
    }

    private static void mockAllBooksRequest() {
        new MockServerClient(SERVER_ADDRESS, serverPort)
          .when(
            request()
              .withPath(PATH)
              .withMethod(HttpMethod.GET.name()),
            exactly(1)
          )
          .respond(
            response()
              .withStatusCode(HttpStatus.SC_OK)
              .withContentType(MediaType.APPLICATION_JSON)
              .withBody("[{\"id\":1,\"title\":\"Book_1\",\"author\":\"Author_1\",\"year\":1998},{\"id\":2,\"title\":\"Book_2\",\"author\":\"Author_2\",\"year\":1999}]")
          );
    }

    private static void mockBookByIdRequest() {
        new MockServerClient(SERVER_ADDRESS, serverPort)
          .when(
            request()
              .withPath(PATH + "/1")
              .withMethod(HttpMethod.GET.name()),
            exactly(1)
          )
          .respond(
            response()
              .withStatusCode(HttpStatus.SC_OK)
              .withContentType(MediaType.APPLICATION_JSON)
              .withBody("{\"id\":1,\"title\":\"Book_1\",\"author\":\"Author_1\",\"year\":1998}")
          );
    }

    private static void mockSaveBookRequest() {
        new MockServerClient(SERVER_ADDRESS, serverPort)
          .when(
            request()
              .withPath(PATH)
              .withMethod(HttpMethod.POST.name())
              .withContentType(MediaType.APPLICATION_JSON)
              .withBody("{\"id\":3,\"title\":\"Book_3\",\"author\":\"Author_3\",\"year\":2000}"),
            exactly(1)
          )
          .respond(
            response()
              .withStatusCode(HttpStatus.SC_OK)
              .withContentType(MediaType.APPLICATION_JSON)
              .withBody("{\"id\":3,\"title\":\"Book_3\",\"author\":\"Author_3\",\"year\":2000}")
          );
    }

    private static void mockDeleteBookRequest() {
        new MockServerClient(SERVER_ADDRESS, serverPort)
          .when(
            request()
              .withPath(PATH + "/3")
              .withMethod(HttpMethod.DELETE.name()),
            exactly(1)
          )
          .respond(
            response()
              .withStatusCode(HttpStatus.SC_OK)
          );
    }

}