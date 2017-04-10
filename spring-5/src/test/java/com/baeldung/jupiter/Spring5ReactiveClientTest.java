package com.baeldung.jupiter;

import com.baeldung.web.reactive.Task;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.ExchangeFunctions;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.net.URI;
import java.time.Duration;
import static org.assertj.core.api.Assertions.assertThat;

public class Spring5ReactiveClientTest {

    private ExchangeFunction exchange = ExchangeFunctions
      .create(new ReactorClientHttpConnector());

    @Test
    public void givenCheckTask_whenServerHandle_thenServerResponseALiveString()
      throws Exception {
        WebClient client = WebClient.create("http://localhost:8080");
        Mono<String> result = client.get()
          .uri("/task")
          .exchange()
          .then(response -> response.bodyToMono(String.class));

        assertThat(result.block()).isInstanceOf(String.class);
    }

    @Test
    public void givenThreeTasks_whenServerHandleTheTasks_thenServerResponseATask()
      throws Exception {
        URI uri = URI.create("http://localhost:8080/task/process");
        ClientRequest request = ClientRequest.method(HttpMethod.POST, uri)
          .body(BodyInserters.fromPublisher(getLatLngs(), Task.class)).build();

        Flux<Task> taskResponse = exchange.exchange(request)
          .flatMap(response -> response.bodyToFlux(Task.class));

        assertThat(taskResponse.blockFirst()).isInstanceOf(Task.class);
    }

    @Test
    public void givenCheckTask_whenServerHandle_thenOragicServerResponseALiveString()
      throws Exception {
        URI uri = URI.create("http://localhost:8080/task");
        ClientRequest request = ClientRequest.method(HttpMethod.GET, uri)
          .body(BodyInserters.fromPublisher(getLatLngs(), Task.class)).build();

        Flux<String> taskResponse = exchange.exchange(request)
          .flatMap(response -> response.bodyToFlux(String.class));

        assertThat(taskResponse.blockFirst()).isInstanceOf(String.class);
    }

    private static Flux<Task> getLatLngs() {
        return Flux.range(0, 3)
          .zipWith(Flux.interval(Duration.ofSeconds(1)))
          .map(x -> new Task("taskname", 1))
          .doOnNext(ll -> System.out.println("Produced: {}" + ll));
    }
}
