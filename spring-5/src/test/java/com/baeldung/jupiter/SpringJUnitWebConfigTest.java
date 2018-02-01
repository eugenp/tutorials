package com.baeldung.jupiter;

import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.web.context.WebApplicationContext;

/**
 * @SpringJUnitWebConfig(SpringJUnitWebConfigTest.Config.class) is equivalent to:
 * 
 * @ExtendWith(SpringExtension.class)
 * @WebAppConfiguration
 * @ContextConfiguration(classes = SpringJUnitWebConfigTest.Config.class )
 *
 */
@SpringJUnitWebConfig(SpringJUnitWebConfigTest.Config.class)
public class SpringJUnitWebConfigTest {

    @Configuration
    static class Config {
    }

    @Autowired
    private WebApplicationContext webAppContext;

    @Test
    void givenWebAppContext_WhenInjected_ThenItShouldNotBeNull() {
        assertNotNull(webAppContext);
    }

}
