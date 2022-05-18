package com.baeldung.httpclient.readresponsebodystring;

import org.junit.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class SpringWebClientUnitTest {
    public static final String DUMMY_URL = "https://postman-echo.com/get";

    @Test
    public void whenUseWebClientRetrieve_thenCorrect() {
        WebClient webClient = WebClient.create(DUMMY_URL);
        Mono<String> body = webClient.get().retrieve().bodyToMono(String.class);
        String s = body.block();
        System.out.println(s);
    }
}
