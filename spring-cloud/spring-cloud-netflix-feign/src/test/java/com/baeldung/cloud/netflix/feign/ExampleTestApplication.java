package com.baeldung.cloud.netflix.feign;

import com.baeldung.cloud.netflix.feign.config.ClientConfiguration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableAutoConfiguration
@ContextConfiguration(classes = { ClientConfiguration.class })
public class ExampleTestApplication {
    
    @Test
    public void whenSpringContextIsBootstrapped_thenNoExceptions() {
    }
}
