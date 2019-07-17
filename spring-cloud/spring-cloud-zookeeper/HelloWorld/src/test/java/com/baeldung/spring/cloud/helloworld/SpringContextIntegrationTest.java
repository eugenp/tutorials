package com.baeldung.spring.cloud.helloworld;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HelloWorldApplication.class)
public class SpringContextIntegrationTest {
    
    @Test
    public void whenSpringContextIsBootstrapped_thenNoExceptions() {
    }
}