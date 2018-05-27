package com.baeldung.reactive.alex.comsa;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;

@Ignore
public class WebFluxClientTest {


    WebClient client = WebClient
            .builder()
            .baseUrl("http://localhost:9090")
            .defaultCookie("cookieKey", "cookieValue")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .defaultUriVariables(Collections.singletonMap("url", "http://localhost:9090"))
            .build();

    @Test(timeout = 10000 )
    public void testAnnotationEndpoint() {

        client.get().uri("/annotation/flight").exchange().block()
                .bodyToMono(String.class)
                .log().block();

    }


    @Test(timeout = 10000 )
    public void testFunctionalEndpoint() {

        client.get().uri("/functional/flight").exchange().block()
                .bodyToMono(String.class)
                .log().block();

    }


}
