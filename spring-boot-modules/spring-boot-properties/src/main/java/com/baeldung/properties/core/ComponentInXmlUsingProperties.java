package com.baeldung.properties.core;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;

public class ComponentInXmlUsingProperties implements InitializingBean {

    @Autowired
    private Environment env;

    @Value("${key.something}")
    private String injectedProperty;

    public ComponentInXmlUsingProperties(final String propertyValue) {
        super();

        System.out.println("Constructor Injection - Property Value resolved to: " + propertyValue);
    }

    //

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("in afterPropertiesSet via @Value: " + injectedProperty);
        System.out.println("in afterPropertiesSet Environment: " + env.getProperty("key.something"));
    }

}
