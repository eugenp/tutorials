package com.baeldung.spring.kafka.sasl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = KafkaSaslApplication.class)
class SprintContextTest {

    @Test
    void whenSpringContextIsBootstrapped_thenNoExceptions() {
    }
}
