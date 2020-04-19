package com.baeldung.reactive;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import com.baeldung.reactive.model.Foo;
import com.github.tomakehurst.wiremock.WireMockServer;

import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReactiveIntegrationTest {

    private WebClient client;
    private int singleRequestTime = 1000;
    private WireMockServer wireMockServer;
    
    @Before
    public void setup() {
        wireMockServer = new WireMockServer(wireMockConfig().dynamicPort());
        wireMockServer.start();
        configureFor("localhost", wireMockServer.port());
        client = WebClient.create("http://localhost:" + wireMockServer.port());
    }

    @After
    public void tearDown() {
        wireMockServer.stop();
    }

    @Test
    public void whenMonoReactiveEndpointIsConsumed_thenCorrectOutput() {
        stubFor(get(urlEqualTo("/foo/123")).willReturn(aResponse().withFixedDelay(singleRequestTime)
            .withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withBody("{\"id\":123, \"name\":\"foo\"}")));
        
        final Mono<ClientResponse> fooMono = client.get().uri("/foo/123").exchange().log();

        System.out.println(fooMono.subscribe());
    }

    @Test
    public void whenFluxReactiveEndpointIsConsumed_thenCorrectOutput() throws InterruptedException {
        stubFor(get(urlEqualTo("/foo")).willReturn(aResponse().withFixedDelay(singleRequestTime)
            .withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withBody("{\"id\":1, \"name\":\"foo\"}")));
        
        client.get().uri("/foo")
            .retrieve()
            .bodyToFlux(Foo.class).log()
            .subscribe(System.out::println);

        System.out.println();
    }

}
