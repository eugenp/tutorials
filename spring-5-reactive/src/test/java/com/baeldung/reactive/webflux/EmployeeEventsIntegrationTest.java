package com.baeldung.reactive.webflux;

import static com.baeldung.reactive.webflux.EmployeeMonitoringController.CHECK_IN;
import static com.baeldung.reactive.webflux.EmployeeMonitoringController.CHECK_OUT;

import com.baeldung.reactive.model.EmployeeEvent;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = EmployeeSpringApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EmployeeEventsIntegrationTest {

    @Autowired
    private WebTestClient testClient;

    @Test
    public void givenEmployeeEventsGenerated_whenWebClientSubscribes_thenEventsAreFiredEverySecond() {

        FluxExchangeResult<EmployeeEvent> eventsResponse = testClient.get()
            .uri("/employee-monitoring/events")
            .accept(MediaType.APPLICATION_STREAM_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .returnResult(EmployeeEvent.class);

        Flux<EmployeeEvent> eventsFlux = eventsResponse.getResponseBody();
        StepVerifier.create(eventsFlux)
            .expectNext(new EmployeeEvent(0, CHECK_IN))
            .expectNextCount(2)
            .expectNext(new EmployeeEvent(3, CHECK_OUT))
            .thenCancel()
            .verify();
    }
}
