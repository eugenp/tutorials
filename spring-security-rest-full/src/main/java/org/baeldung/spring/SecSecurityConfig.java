package org.baeldung.spring;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@EnableAutoConfiguration
@ImportResource({ "classpath:webSecurityConfig.xml" })
public class SecSecurityConfig {

    public SecSecurityConfig() {
        super();
    }

}
