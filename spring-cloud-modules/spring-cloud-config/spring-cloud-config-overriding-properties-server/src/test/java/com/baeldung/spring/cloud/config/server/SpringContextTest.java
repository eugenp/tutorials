package com.baeldung.spring.cloud.config.server;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.baeldung.spring.cloud.config.overridingproperties.ConfigServer;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ConfigServer.class)
@ActiveProfiles("native")
public class SpringContextTest {

    @Test
    public void whenSpringContextIsBootstrapped_thenNoExceptions() {
    }
}
