package com.baeldung.httpclient.readresponsebodystring;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class SpringWebClientUnitTest {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    public static final String DUMMY_URL = "https://postman-echo.com/get";

    @Test
    void whenUseWebClientRetrieve_thenCorrect() {
        WebClient webClient = WebClient.create(DUMMY_URL);
        Mono<String> body = webClient.get().retrieve().bodyToMono(String.class);
        String s = body.block();
        logger.debug(s);
    }
}
