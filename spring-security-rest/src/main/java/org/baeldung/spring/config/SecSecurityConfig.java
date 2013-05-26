package org.baeldung.spring.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource({ "classpath:webSecurityConfig.xml" })
@ComponentScan("org.baeldung.spring.security")
public class SecSecurityConfig {

    public SecSecurityConfig() {
        super();
    }

}
