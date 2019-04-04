package com.baeldung.spring.cloud.config.server;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;


/**
 * 
 * The context will load successfully with some properties provided by docker
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ConfigServer.class)
@WebAppConfiguration
public class SpringContextLiveTest {
    @Test
    public void whenSpringContextIsBootstrapped_thenNoExceptions() {
    }
}
