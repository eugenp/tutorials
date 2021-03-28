package com.baeldung.webclient.json;

import com.baeldung.webclient.json.model.Book;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasProperty;

public class ReaderConsumerServiceImplUnitTest {

    private static String READER_JSON = "[{\"id\":1,\"name\":\"reader1\",\"favouriteBook\":{\"author\":\"Milan Kundera\",\"title\":\"The Unbearable Lightness of Being\"}," +
      "\"booksRead\":[{\"author\":\"Charles Dickens\",\"title\":\"Oliver Twist\"},{\"author\":\"Milan Kundera\",\"title\":\"The Unbearable Lightness of Being\"}]}," +
      "{\"id\":2,\"name\":\"reader2\",\"favouriteBook\":{\"author\":\"Douglas Adams\",\"title\":\"The Hitchhiker\'s Guide to the Galaxy\"}," +
      "\"booksRead\":[{\"author\":\"J.R.R. Tolkien\",\"title\":\"Lord of the Rings\"}, " +
      "{\"author\":\"Douglas Adams\",\"title\":\"The Hitchhiker\'s Guide to the Galaxy\"}]}]";

    private static String BASE_URL = "http://localhost:8080/readers";

    WebClient webClientMock = WebClient.builder().baseUrl(BASE_URL)
      .exchangeFunction(clientRequest -> Mono.just(ClientResponse.create(HttpStatus.OK)
        .header("content-type", "application/json")
        .body(READER_JSON)
        .build()))
      .build();

    private final ReaderConsumerService tested = new ReaderConsumerServiceImpl(webClientMock);

    @Test
    void when_processReaderDataFromObjectArray_then_OK() {
        String expectedAuthor1 = "Milan Kundera";
        String expectedAuthor2 = "Douglas Adams";
        List<Book> actual = tested.processReaderDataFromObjectArray();
        assertThat(actual, hasItems(hasProperty("author", is(expectedAuthor1)),
          hasProperty("author", is(expectedAuthor2))));
    }

    @Test
    void when_processReaderDataFromReaderArray_then_OK() {
        String expectedAuthor1 = "Milan Kundera";
        String expectedAuthor2 = "Douglas Adams";
        List<Book> actual = tested.processReaderDataFromReaderArray();
        assertThat(actual, hasItems(hasProperty("author", is(expectedAuthor1)),
          hasProperty("author", is(expectedAuthor2))));
    }

    @Test
    void when_processReaderDataFromReaderList_then_OK() {
        String expectedAuthor1 = "Milan Kundera";
        String expectedAuthor2 = "Douglas Adams";
        List<Book> actual = tested.processReaderDataFromReaderList();
        assertThat(actual, hasItems(hasProperty("author", is(expectedAuthor1)),
          hasProperty("author", is(expectedAuthor2))));

    }

    @Test
    void when_processNestedReaderDataFromReaderArray_then_OK() {
        List<String> expected = Arrays.asList(
          "Milan Kundera",
          "Charles Dickens",
          "J.R.R. Tolkien",
          "Douglas Adams");

        List<String> actual = tested.processNestedReaderDataFromReaderArray();
        assertThat(actual, hasItems(expected.get(0), expected.get(1), expected.get(2), expected.get(3)));
    }

    @Test
    void when_processNestedReaderDataFromReaderList_then_OK() {
        List<String> expected = Arrays.asList(
          "Milan Kundera",
          "Charles Dickens",
          "J.R.R. Tolkien",
          "Douglas Adams");

        List<String> actual = tested.processNestedReaderDataFromReaderList();
        assertThat(actual, hasItems(expected.get(0), expected.get(1), expected.get(2), expected.get(3)));
    }
}