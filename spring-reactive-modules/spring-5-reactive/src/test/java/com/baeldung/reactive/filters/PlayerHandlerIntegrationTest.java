package com.baeldung.reactive.filters;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@WithMockUser
@AutoConfigureWebTestClient(timeout = "10000")
public class PlayerHandlerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void whenPlayerNameIsBaeldung_thenWebFilterIsApplied() {
        EntityExchangeResult<String> result = webTestClient.get().uri("/players/baeldung")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .returnResult();

        assertEquals(result.getResponseBody(), "baeldung");
        assertEquals(result.getResponseHeaders().getFirst("web-filter"), "web-filter-test");
    }

    @Test
    public void whenPlayerNameIsTest_thenHandlerFilterFunctionIsApplied() {
        webTestClient.get().uri("/players/test")
                .exchange()
                .expectStatus().isForbidden();
    }

}