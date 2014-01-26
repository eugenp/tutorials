package org.baeldung.config.parent;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource({ "classpath:webSecurityConfig.xml" })
@ComponentScan("org.baeldung.security")
public class SecurityConfig {

    public SecurityConfig() {
        super();
    }

}
