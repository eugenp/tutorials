package org.baeldung;

import org.baeldung.custom.config.MvcConfig;
import org.baeldung.custom.config.PersistenceDerbyJPAConfig;
import org.baeldung.custom.config.SecSecurityConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { MvcConfig.class, PersistenceDerbyJPAConfig.class, SecSecurityConfig.class })
public class SpringContextIntegrationTest {

    @Test
    public void whenSpringContextIsBootstrapped_thenNoExceptions() {
    }
}
