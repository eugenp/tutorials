package com.baeldung.comparison.shiro;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.baeldung.comparison.shiro.ShiroApplication;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { ShiroApplication.class })
public class SpringContextTest {

    @Test
    public void whenSpringContextIsBootstrapped_thenNoExceptions() {

    }

}