package org.baeldung.startup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class PostConstructExampleBean {

    @Autowired
    private Environment environment;

    public PostConstructExampleBean() {
    }
}
