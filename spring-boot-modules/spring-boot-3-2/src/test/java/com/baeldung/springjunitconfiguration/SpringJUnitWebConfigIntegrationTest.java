package com.baeldung.springjunitconfiguration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertNotNull;

/**
 * @SpringJUnitWebConfig(SpringJUnitWebConfigTest.Config.class) is equivalent to:
 *
 * @ExtendWith(SpringExtension.class)
 * @WebAppConfiguration
 * @ContextConfiguration(classes = SpringJUnitWebConfigTest.Config.class )
 *
 */
@SpringJUnitWebConfig(SpringJUnitWebConfigIntegrationTest.Config.class)
public class SpringJUnitWebConfigIntegrationTest {

    @Autowired
    private WebApplicationContext webAppContext;

    @Test
    void givenWebAppContext_WhenInjected_ThenItShouldNotBeNull() {
        assertNotNull(webAppContext);
    }

    @Configuration
    static class Config {
    }

}
