package com.baeldung.web.client;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.baeldung.web.reactive.client.WebClientApplication;

@SpringBootTest(classes = WebClientApplication.class)
public class SpringContextTest {

    @Test
    public void whenSpringContextIsBootstrapped_thenNoExceptions() {
    }
}
