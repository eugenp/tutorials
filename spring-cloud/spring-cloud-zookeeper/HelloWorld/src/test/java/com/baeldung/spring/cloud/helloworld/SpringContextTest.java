package com.baeldung.spring.cloud.helloworld;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = HelloWorldApplication.class)
public class SpringContextTest {
    
    @Test
    public void whenSpringContextIsBootstrapped_thenNoExceptions() {
    }
}
