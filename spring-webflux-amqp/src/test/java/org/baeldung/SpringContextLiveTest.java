package org.baeldung;

import org.baeldung.spring.amqp.SpringWebfluxAmqpApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * This live test requires:
 * rabbitmq instance running on the environment
 * 
 * <br>
 * To run rabbitmq using docker image:
 * (e.g. `docker run -d --name rabbitmq -p 5672:5672 rabbitmq:3`)
 * 
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringWebfluxAmqpApplication.class)
public class SpringContextLiveTest {

    @Test
    public void whenSpringContextIsBootstrapped_thenNoExceptions() {
    }
}
