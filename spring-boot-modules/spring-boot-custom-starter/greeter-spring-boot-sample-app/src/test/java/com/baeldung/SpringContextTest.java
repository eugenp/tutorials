package com.baeldung;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.greeter.GreeterSampleApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GreeterSampleApplication.class)
@ComponentScan("com.baeldung")
public class SpringContextTest {

    @Test
    public void whenSpringContextIsBootstrapped_thenNoExceptions() {
    }
}
