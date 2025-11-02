package com.baeldung.spring.resttestclient;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.client.RestTestClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
public class RestTestClientTest {

    @Autowired
    private MyController myController;

    private RestTestClient restTestClient;

    @BeforeEach
    void beforeEach(WebApplicationContext context) {
        var restTestClientBuilder = RestTestClient.bindToController(myController);
        restTestClient = restTestClientBuilder
            //.baseUrl("/public") // 1)
            //.defaultHeader("ContentType", "application/json") // 2)
            //.defaultCookie("JSESSIONID", "abc123def456ghi789") // 3)
            .build();

    }

    @Test
    void givenValidPath_WhenCalled_ThenReturnOk() {
        restTestClient.get()
            .uri("/persons/1")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(Person.class)
            .isEqualTo(new Person(1L, "John Doe"));
    }

    @Test
    void givenWrongCallType_WhenCalled_ThenReturnClientError() {
        restTestClient.post() // <=== wrong type
            .uri("/persons/1")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .is4xxClientError();
    }

    @Test
    void givenWrongId_WhenCalled_ThenReturnNoContent() {
        restTestClient.get()
            .uri("/persons/0") // <=== wrong id
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();
    }

    @Test
    void givenInvalidPath_WhenCalled_ThenReturnNotFound() {
        restTestClient.get()
            .uri("/invalid")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void givenValidId_whenGetPerson_thenReturnsCorrectFields() {
        restTestClient.get()
            .uri("/persons/1")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody()
            .jsonPath("$.id")
            .isEqualTo(1)
            .jsonPath("$.name")
            .isEqualTo("John Doe");
    }

    @Test
    void givenValidRequest_whenGetPerson_thenPassesAllAssertions() {
        restTestClient.get()
            .uri("/persons/1")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(Person.class)
            .consumeWith(result -> {
                assertThat(result.getStatus().value()).isEqualTo(200);
                assertThat(result.getResponseBody().name()).isEqualTo("John Doe");
            });
    }
}

@RestController("my")
class MyController {

    @GetMapping("/persons/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable Long id) {
        return id == 1 ? ResponseEntity.ok(new Person(1L, "John Doe")) : ResponseEntity.noContent()
            .build();
    }
}

record Person(Long id, String name) {

}
