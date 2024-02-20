package com.baeldung;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import com.baeldung.spring.MvcConfig;
import com.baeldung.spring.SecSecurityConfig;

@WebAppConfiguration
@ContextConfiguration(classes = { MvcConfig.class, SecSecurityConfig.class })
@ExtendWith(SpringExtension.class)
class SpringContextTest {

    @Test
    void whenSpringContextIsBootstrapped_thenNoExceptions() {
    }
}


