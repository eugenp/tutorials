package com.baeldung;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.client.RmiClient;

/**
 * This Live Test requires:
 * * the `com.baeldung:remoting-http-api:jar:1.0-SNAPSHOT` artifact accessible. For that we can run `mvn clean install` in the 'spring-remoting/remoting-http/remoting-http-api' module.
 * * the 'spring-remoting\remoting-rmi\remoting-rmi-server' service running
 *
 */
@SpringBootTest(classes = RmiClient.class)
@RunWith(SpringRunner.class)
public class SpringContextLiveTest {

    @Test
    public void whenSpringContextIsBootstrapped_thenNoExceptions() {
    }
}
