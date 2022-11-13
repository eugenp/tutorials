package com.baeldung;

import com.baeldung.spring.jinq.JinqApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.baeldung.spring.jinq.JinqApplication;

@SpringBootTest(classes = JinqApplication.class)
public class SpringContextTest {

    @Test
    public void whenSpringContextIsBootstrapped_thenNoExceptions() {
    }
}
