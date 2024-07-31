package com.baeldung.properties.core;

import com.baeldung.properties.spring.PropertiesWithJavaConfig;
import com.baeldung.properties.spring.PropertiesWithJavaConfigOther;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { PropertiesWithJavaConfig.class, PropertiesWithJavaConfigOther.class }, loader = AnnotationConfigContextLoader.class)
public class PropertiesWithJavaManualTest {

    @Autowired
    private Environment env;

    @Value("${key.something}")
    private String injectedProperty;

    @Test
    public final void givenContextIsInitialized_thenNoException() {
        System.out.println("in test via @Value: " + injectedProperty);
        System.out.println("in test Environment: " + env.getProperty("key.something"));
    }

}
