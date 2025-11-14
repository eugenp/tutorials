package com.baeldung.spring.resttestclient;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.client.RestTestClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootTest
public class RestTestClientUnitTest {

    @Autowired
    private MyController myController;

    @Autowired
    private AnotherController anotherController;

    @Autowired
    private RestTestClient restTestClient;

    @BeforeEach
    void beforeEach(WebApplicationContext context) {
        restTestClient = RestTestClient.bindToController(myController, anotherController)
            .build();
    }

    @Test
    void givenValidPath_whenCalled_thenReturnOk() {
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
    void givenWrongCallType_whenCalled_thenReturnClientError() {
        restTestClient.post() // <=== wrong type
            .uri("/persons/1")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .is4xxClientError();
    }

    @Test
    void givenWrongId_whenCalled_thenReturnNoContent() {
        restTestClient.get()
            .uri("/persons/0") // <=== wrong id
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();
    }

    @Test
    void givenInvalidPath_whenCalled_thenReturnNotFound() {
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
                assertThat(result.getStatus()
                    .value()).isEqualTo(200);
                assertThat(result.getResponseBody()
                    .name()).isEqualTo("John Doe");
            });
    }

    @Test
    void givenValidQuery_whenGetPersonsStream_thenReturnsFlux() {
        restTestClient.get()
            .uri("/persons")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(new ParameterizedTypeReference<List<Person>>() {});
    }

    @Test
    void givenValidQueryToSecondController_whenGetPenguinMono_thenReturnsEmpty() {
        restTestClient.get()
            .uri("/pink/penguin")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(Penguin.class)
            .value(it -> assertThat(it).isNull());
    }
}

@RestController("my")
class MyController {

    @GetMapping("/persons/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable Long id) {
        return id == 1 ? ResponseEntity.ok(new Person(1L, "John Doe")) : ResponseEntity.noContent()
            .build();
    }

    @GetMapping("/persons")
    public Flux<Person> getAllPersons() {
        var persons = List.of(
            new Person(1L, "John Doe"),
            new Person(2L, "James Bond"),
            new Person(3L, "Alice In Wonderland")
        );
        return Flux.fromIterable(persons);
    }
}

@RestController("my2")
class AnotherController {

    @GetMapping("/pink/penguin")
    public Mono<Penguin> getPinkPenguin() {
        return Mono.empty();
    }
}

record Person(Long id, String name) { }
record Penguin(Long id) { }
