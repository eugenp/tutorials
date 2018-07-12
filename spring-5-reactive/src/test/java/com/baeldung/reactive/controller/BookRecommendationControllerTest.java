package com.baeldung.reactive.controller;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.hamcrest.Matchers.isIn;
import static org.junit.Assert.assertThat;

public class BookRecommendationControllerTest {

    private WebTestClient client = WebTestClient
      .bindToController(new BookRecommendationController())
      .build();

    @Test
    public void whenGetBookNames_thenCorrectOutput() {
        String actualOutput = client.get().uri("/books/names")
          .exchange()
          .expectStatus().isOk()
          .expectHeader().contentTypeCompatibleWith(MediaType.TEXT_EVENT_STREAM_VALUE)
          .returnResult(String.class)
          .getResponseBody()
          .next()
          .block();

        assertThat(actualOutput, isIn(BookRecommendationController.BOOK_NAMES));
    }
}