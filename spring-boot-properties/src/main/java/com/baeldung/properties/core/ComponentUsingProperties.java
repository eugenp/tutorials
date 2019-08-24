package com.baeldung.properties.core;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class ComponentUsingProperties implements InitializingBean {

    @Autowired
    private Environment env;

    @Value("${key.something}")
    private String injectedProperty;

    public ComponentUsingProperties() {
        super();
    }

    //

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("in afterPropertiesSet via @Value: " + injectedProperty);
        System.out.println("in afterPropertiesSet Environment: " + env.getProperty("key.something"));
    }

}
