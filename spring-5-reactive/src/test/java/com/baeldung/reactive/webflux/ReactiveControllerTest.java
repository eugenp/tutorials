package webflux;

import java.time.Duration;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.baeldung.ReactiveController;

import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ReactiveController.class)
@WebFluxTest(controllers = ReactiveController.class)
public class ReactiveControllerTest {

    private WebTestClient webClient;

    @Mock
    private FluxExchangeResult<Date> serverEvents;

    @Before
    public void setup() {
        webClient = WebTestClient.bindToController(new ReactiveController())
            .build();
    }

    @Test
    public void givenReactiveEndpoint_whenSubscribed_thenEventPerSecond() {
        FluxExchangeResult<Date> result = webClient.get()
            .uri("/serverSentEvents")
            .accept(MediaType.TEXT_EVENT_STREAM)
            .exchange()
            .expectStatus()
            .isOk()
            .returnResult(Date.class);

        StepVerifier.create(result.getResponseBody())
            .expectSubscription()
            .thenAwait(Duration.ofSeconds(5))
            .expectNextCount(5)
            .thenCancel()
            .verify();
    }

}
