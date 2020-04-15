package com.baeldung.properties.external;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.baeldung.properties.external.ExternalPropertiesWithXmlConfigOne;
import com.baeldung.properties.external.ExternalPropertiesWithXmlConfigTwo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ExternalPropertiesWithXmlConfigOne.class, ExternalPropertiesWithXmlConfigTwo.class }, loader = AnnotationConfigContextLoader.class)
@Ignore("manual only")
public class ExternalPropertiesWithMultipleXmlsIntegrationTest {

    @Autowired
    private Environment env;

    @Value("${key.something}")
    private String injectedProperty;

    @Value("${external.something}")
    private String injectedExternalProperty;

    @Test
    public final void givenContextIsInitialized_thenNoException() {
        System.out.println("in test via @Value: " + injectedProperty);
        System.out.println("in test Environment: " + env.getProperty("key.something"));

        System.out.println("in test via @Value - external: " + injectedExternalProperty);
        System.out.println("in test Environment - external: " + env.getProperty("external.something"));
    }

}
