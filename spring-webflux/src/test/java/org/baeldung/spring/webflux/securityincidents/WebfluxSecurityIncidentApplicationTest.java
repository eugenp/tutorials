package org.baeldung.spring.webflux.securityincidents;

import org.baeldung.spring.webflux.securityincidents.domain.SecurityEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebfluxSecurityIncidentApplicationTest {
    @Autowired
    private WebTestClient webTestClient;

    private final static int UUID_LENGTH = 36;

    @Test
    public void whenConsumingJson_thenReturnsCorrectContent() throws InterruptedException {
        webTestClient.get()
            .uri("/securityevents/1")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBodyList(SecurityEvent.class)
            .hasSize(1)
            .consumeWith(securityEvents -> {
                
                assertThat(securityEvents.getResponseBody()).hasSize(1);
                assertThat(securityEvents.getResponseBody())
                    .allSatisfy(securityEvent -> assertThat(securityEvent.getId()
                    .toString()
                    .length()).isEqualTo(UUID_LENGTH))
                    .allSatisfy(securityEvent -> assertThat(securityEvent.getName()
                        .length()).isPositive())
                    .allSatisfy(securityEvent -> assertThat(securityEvent.getTimestamp()).isInThePast());
            });
    }

    @Test
    public void whenConsumingJsonStream_thenReturnsCorrectContent() throws InterruptedException {

        webTestClient.get()
            .uri("/securityeventsstream")
            .accept(MediaType.APPLICATION_STREAM_JSON)
            .exchange()
            .returnResult(SecurityEvent.class)
            .getResponseBody()
            .take(1)
            .subscribe(securityEvent -> {
                assertThat(securityEvent.getId()
                    .toString()
                    .length()).isEqualTo(UUID_LENGTH);
                assertThat(securityEvent.getName()
                    .length()).isPositive();
                assertThat(securityEvent.getTimestamp()).isInThePast();

            });
    }

}
