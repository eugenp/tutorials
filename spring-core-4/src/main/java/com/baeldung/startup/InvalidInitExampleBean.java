package com.baeldung.startup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class InvalidInitExampleBean {

    @Autowired
    private Environment environment;

    public InvalidInitExampleBean() {
        environment.getActiveProfiles();
    }
}
