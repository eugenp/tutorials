package com.baeldung;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.web.context.WebApplicationContext;

@SpringJUnitWebConfig(AppContextIntegrationTest.Config.class)
public class AppContextIntegrationTest {
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
