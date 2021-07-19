package com.baeldung.overrideproperties;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.support.TestPropertySourceUtils;

public class PropertyOverrideContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    static final String PROPERTY_FIRST_VALUE = "contextClass";

    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        TestPropertySourceUtils.addInlinedPropertiesToEnvironment(configurableApplicationContext, "example.firstProperty=" + PROPERTY_FIRST_VALUE);

        TestPropertySourceUtils.addPropertiesFilesToEnvironment(configurableApplicationContext, "classpath:context-override-application.properties");
    }
}
