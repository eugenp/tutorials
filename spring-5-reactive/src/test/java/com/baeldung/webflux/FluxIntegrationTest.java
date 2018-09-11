package com.baeldung.webflux;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FluxIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void testReturnedFlux() {
        List<String> resultList = webTestClient.get()
            .uri("/produce")
            .exchange()
            .expectStatus()
            .isOk()
            .returnResult(String.class)
            .getResponseBody()
            .take(3)
            .collectList()
            .block();

        assertThat(resultList.size()).isEqualTo(3);
        assertThat(resultList).allSatisfy(result -> assertThat(result.startsWith("Spring WebFlux - ")));
    }
}