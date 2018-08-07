package com.baeldung;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ServerIntegrationTests {

    @Autowired WebTestClient webTestClient;

    @Test
    public void givenCurrentTimeEventsGenerated_whenClientSubscribes_thenEventsGeneratedEverySecond() {
        FluxExchangeResult<Long> result = webTestClient
                                                  .get()
                                                  .uri("/data")
                                                  .accept(MediaType.TEXT_EVENT_STREAM)
                                                  .exchange()
                                                  .expectStatus()
                                                  .isOk()
                                                  .returnResult(Long.class);
    }
}
