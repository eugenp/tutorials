package com.baeldung.reactive.filters;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.Assert.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void whenUserNameIsBaeldung_thenWebFilterIsApplied() {
        EntityExchangeResult<String> result = webTestClient.get().uri("/users/baeldung")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .returnResult();

        assertEquals(result.getResponseBody(), "baeldung");
        assertEquals(result.getResponseHeaders().getFirst("web-filter"), "web-filter-test");
    }

    @Test
    public void whenUserNameIsTest_thenHandlerFilterFunctionIsNotApplied() {
        webTestClient.get().uri("/users/test")
                .exchange()
                .expectStatus().isOk();
    }
}