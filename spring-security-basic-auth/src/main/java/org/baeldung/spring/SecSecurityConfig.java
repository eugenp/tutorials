package org.baeldung.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("org.baeldung.security")
//@ImportResource({ "classpath:webSecurityConfig.xml" })
public class SecSecurityConfig {

    public SecSecurityConfig() {
        super();
    }

}
