package org.baeldung.startup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "prototype")
public class PostConstructExampleBean {

    @Autowired
    private Environment environment;

    public PostConstructExampleBean() {
    }
}
