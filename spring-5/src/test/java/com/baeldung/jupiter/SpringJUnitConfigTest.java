package com.baeldung.jupiter;

import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

/**
 * @SpringJUnitConfig(SpringJUnitConfigTest.Config.class) is equivalent to:
 * 
 * @ExtendWith(SpringExtension.class)
 * @ContextConfiguration(classes = SpringJUnitConfigTest.Config.class )
 *
 */
@SpringJUnitConfig(SpringJUnitConfigTest.Config.class)
public class SpringJUnitConfigTest {

    @Configuration
    static class Config {
    }

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void givenAppContext_WhenInjected_ThenItShouldNotBeNull() {
        assertNotNull(applicationContext);
    }

}
