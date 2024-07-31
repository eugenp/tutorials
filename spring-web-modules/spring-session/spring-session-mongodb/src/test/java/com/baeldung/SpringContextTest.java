package com.baeldung;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.baeldung.springsessionmongodb.SpringSessionMongoDBApplication;

@SpringBootTest(classes = SpringSessionMongoDBApplication.class)
public class SpringContextTest {

    @Test
    public void whenSpringContextIsBootstrapped_thenNoExceptions() {
        // Ensuring the context is spring boot application is started.
    }
}
