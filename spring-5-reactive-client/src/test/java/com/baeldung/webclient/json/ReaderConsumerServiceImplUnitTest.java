package com.baeldung.webclient.json;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

public class ReaderConsumerServiceImplUnitTest {

    private static String READER_JSON = "[{\"id\":1,\"name\":\"reader1\",\"favouriteBooks\":[{\"author\":\"Milan Kundera\",\"title\":\"The Unbearable Lightness of Being\"}," +
      "{\"author\":\"Charles Dickens\",\"title\":\"Oliver Twist\"}]}," +
      "{\"id\":2,\"name\":\"reader2\",\"favouriteBooks\":[{\"author\":\"J.R.R. Tolkien\",\"title\":\"Lord of the Rings\"}, " +
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
        List<String> expected = Arrays.asList("reader1", "reader2");
        List<String> actual = tested.processReaderDataFromObjectArray();
        assertThat(actual, contains(expected.get(0), expected.get(1)));
    }

    @Test
    void when_processReaderDataFromReaderArray_then_OK() {
        List<String> expected = Arrays.asList("reader1", "reader2");
        List<String> actual = tested.processReaderDataFromReaderArray();
        assertThat(actual, contains(expected.get(0), expected.get(1)));
    }

    @Test
    void when_processReaderDataFromReaderList_then_OK() {
        List<String> expected = Arrays.asList("reader1", "reader2");
        List<String> actual = tested.processReaderDataFromReaderList();
        assertThat(actual, contains(expected.get(0), expected.get(1)));
    }

    @Test
    void when_processNestedReaderDataFromReaderArray_then_OK() {
        List<String> expected = Arrays.asList(
          "Milan Kundera",
          "Charles Dickens",
          "J.R.R. Tolkien",
          "Douglas Adams");

        List<String> actual = tested.processNestedReaderDataFromReaderArray();
        assertThat(actual, contains(expected.get(0), expected.get(1), expected.get(2), expected.get(3)));
    }

    @Test
    void when_processNestedReaderDataFromReaderList_then_OK() {
        List<String> expected = Arrays.asList(
          "Milan Kundera",
          "Charles Dickens",
          "J.R.R. Tolkien",
          "Douglas Adams");

        List<String> actual = tested.processNestedReaderDataFromReaderList();
        assertThat(actual, contains(expected.get(0), expected.get(1), expected.get(2), expected.get(3)));
    }
}