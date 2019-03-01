package org.baeldung;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.server.JmsServer;

@SpringBootTest(classes = JmsServer.class)
@RunWith(SpringRunner.class)
public class SpringContextManualTest {

    @Test
    public void whenSpringContextIsBootstrapped_thenNoExceptions() {
    }
}
