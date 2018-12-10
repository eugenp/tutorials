package org.baeldung;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.Application;
import com.baeldung.config.PersistenceConfiguration;
import com.baeldung.config.PersistenceProductConfiguration;
import com.baeldung.config.PersistenceUserConfiguration;

@RunWith(SpringRunner.class)
@DataJpaTest(excludeAutoConfiguration = {PersistenceConfiguration.class, PersistenceUserConfiguration.class, PersistenceProductConfiguration.class})
@SpringBootTest(classes = Application.class)
public class SpringContextIntegrationTest {

    @Test
    public void whenSpringContextIsBootstrapped_thenNoExceptions() {
    }
}
