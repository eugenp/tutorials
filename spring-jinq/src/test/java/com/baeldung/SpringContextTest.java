package com.baeldung;

import com.baeldung.spring.jinq.JinqApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest(classes = JinqApplication.class)
class SpringContextTest {

    @Test
    void whenSpringContextIsBootstrapped_thenNoExceptions() {
    }
}
