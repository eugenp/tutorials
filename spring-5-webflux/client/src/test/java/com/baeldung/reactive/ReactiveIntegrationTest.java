package com.baeldung.reactive;

import com.baeldung.reactive.model.Foo;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@SpringBootTest
public class ReactiveIntegrationTest {

    private WebClient client;

    @Before
    public void before() {
        client = WebClient.create("http://localhost:8080");
    }

    @Test
    public void whenMonoReactiveEndpointIsConsumed_thenCorrectOutput() {

        WebClient client = WebClient.create("http://localhost:8080");
        final Mono<ClientResponse> fooMono
                = client.get()
                .uri("/foos/123")
                .exchange().log();

        fooMono.subscribe(System.out::println);
    }

    @Test
    public void whenFluxReactiveEndpointIsConsumed_thenCorrectOutput() throws InterruptedException {

        WebClient client = WebClient.create("http://localhost:8080");

        client.get().uri("/foos")
            .retrieve()
            .bodyToFlux(Foo.class)
            .subscribe(System.out::println);

    }

}