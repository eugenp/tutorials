package com.baeldung.shiro;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { ShiroApplication.class })
public class SpringContextTest {

    @Test
    public void whenSpringContextIsBootstrapped_thenNoExceptions() {

    }

}