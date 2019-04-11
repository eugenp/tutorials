package org.baeldung;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.client.BurlapClient;
import com.baeldung.client.HessianClient;

@SpringBootTest(classes = {BurlapClient.class, HessianClient.class})
@RunWith(SpringRunner.class)
public class SpringContextIntegrationTest {

    @Test
    public void whenSpringContextIsBootstrapped_thenNoExceptions() {
    }
}
