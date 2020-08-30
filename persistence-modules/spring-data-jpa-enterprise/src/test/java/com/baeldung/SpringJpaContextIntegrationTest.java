package com.baeldung;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.boot.Application;
import com.baeldung.boot.config.PersistenceConfiguration;
import com.baeldung.multipledb.PersistenceProductConfiguration;
import com.baeldung.multipledb.PersistenceUserConfiguration;

@RunWith(SpringRunner.class)
@DataJpaTest(excludeAutoConfiguration = {
        PersistenceConfiguration.class,
        PersistenceUserConfiguration.class,
        PersistenceProductConfiguration.class })
@ContextConfiguration(classes = Application.class)
public class SpringJpaContextIntegrationTest {

    @Test
    public void whenSpringContextIsBootstrapped_thenNoExceptions() {
    }
}
