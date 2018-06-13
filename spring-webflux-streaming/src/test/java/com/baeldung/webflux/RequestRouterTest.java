package com.baeldung.webflux;

import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.baeldung.webflux.model.WeatherEvent;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
//Boot-Up an actual server
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RequestRouterTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void testWeatherEventStream() throws InterruptedException {
        FluxExchangeResult<WeatherEvent> response = webTestClient.get()
            .uri("/weatherstream")
            .accept(MediaType.TEXT_EVENT_STREAM)
            .exchange()
            .expectStatus()
            .isOk()
            .returnResult(WeatherEvent.class);

        Flux<WeatherEvent> eventFlux = response.getResponseBody();

        //checking if the temperature exist in the array
        Predicate<? super WeatherEvent> checkTemprature = event -> {
            return Arrays.asList("19 C", "19.5 C", "20 C", "20.5 C", "21 C", "21.5 C", "22 C", "22.5 C", "23 C", "23.5 C", "24 C")
                .contains(event.getWeather()
                    .getTemprature());
        };
        StepVerifier.create(eventFlux)
            .expectNextMatches(checkTemprature) //checking if temperature lies in range
            .consumeNextWith(System.out::println) //printing one weather event data
            .thenCancel()
            .verify();

    }
}
