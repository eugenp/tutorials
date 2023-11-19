package com.baeldung.spqr;

import com.baeldung.SpqrBootStarterApp;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT, classes = SpqrBootStarterApp.class)
class SpqrBootStarterAppIntegrationTest {

    private static final String GRAPHQL_PATH = "/graphql";

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void whenGetAllBooks_thenValidResponseReturned() {
        String getAllBooksQuery = "{getAllBooks{ id title author }}";

        webTestClient.post()
          .uri(GRAPHQL_PATH)
          .contentType(MediaType.APPLICATION_JSON)
          .body(Mono.just(toJSON(getAllBooksQuery)), String.class)
          .exchange()
          .expectStatus().isOk()
          .expectBody()
          .jsonPath("$.data.getAllBooks").isNotEmpty();
    }

    @Test
    void whenAddBook_thenValidResponseReturned() {
        String addBookMutation = "mutation { addBook(newBook: {id: 123, author: \"J. K. Rowling\", "
          + "title: \"Harry Potter and Philosopher's Stone\"}) { id author title } }";

        webTestClient.post()
          .uri(GRAPHQL_PATH)
          .contentType(MediaType.APPLICATION_JSON)
          .body(Mono.just(toJSON(addBookMutation)), String.class)
          .exchange()
          .expectStatus().isOk()
          .expectBody()
          .jsonPath("$.data.addBook.id").isEqualTo("123")
          .jsonPath("$.data.addBook.title").isEqualTo("Harry Potter and Philosopher's Stone")
          .jsonPath("$.data.addBook.author").isEqualTo("J. K. Rowling");
    }

    private static String toJSON(String query) {
        try {
            return new JSONObject().put("query", query).toString();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

}
