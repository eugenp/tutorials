package org.baeldung;

import org.baeldung.spring.cloud.JobConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = JobConfiguration.class)
public class SpringContextIntegrationTest {

    @Test
    public void whenSpringContextIsBootstrapped_thenNoExceptions() {
    }
}
