package com.baeldung.spring.cloud.kubernetes.travelagency;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = Application.class)
public class SpringContextTest {
    
    @Test
    public void whenSpringContextIsBootstrapped_thenNoExceptions() {
    }
}
