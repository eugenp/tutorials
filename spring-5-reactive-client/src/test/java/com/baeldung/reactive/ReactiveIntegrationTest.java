package com.baeldung.reactive;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import com.baeldung.reactive.model.Foo;

import reactor.core.publisher.Mono;

@SpringBootTest
public class ReactiveIntegrationTest {

    private WebClient client;

    @BeforeEach
    public void before() {
        client = WebClient.create("http://localhost:8080");
    }

    //

    @Test
    public void whenMonoReactiveEndpointIsConsumed_thenCorrectOutput() {
        final Mono<ClientResponse> fooMono = client.get().uri("/foos/123").exchange().log();

        System.out.println(fooMono.subscribe());
    }

    @Test
    public void whenFluxReactiveEndpointIsConsumed_thenCorrectOutput() throws InterruptedException {
        client.get().uri("/foos")
            .retrieve()
            .bodyToFlux(Foo.class).log()
            .subscribe(System.out::println);

        System.out.println();
    }

}
