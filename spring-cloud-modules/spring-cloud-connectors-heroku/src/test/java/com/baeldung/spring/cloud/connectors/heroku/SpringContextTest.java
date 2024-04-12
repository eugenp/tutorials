package com.baeldung.spring.cloud.connectors.heroku;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.spring.cloud.connectors.heroku.ConnectorsHerokuApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConnectorsHerokuApplication.class)
public class SpringContextTest {

    @Test
    public void whenSpringContextIsBootstrapped_thenNoExceptions() {
    }
}
