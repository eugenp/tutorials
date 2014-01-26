package org.baeldung.config.parent;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("org.baeldung.service")
public class ServiceConfig {

    public ServiceConfig() {
        super();
    }

}