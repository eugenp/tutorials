package org.baeldung.spring;

import org.springframework.context.annotation.ComponentScan;

// @Configuration
// @ImportResource({ "classpath:webSecurityConfig.xml" })
@ComponentScan("org.baeldung.security")
public class SecurityXmlConfig {

    public SecurityXmlConfig() {
        super();
    }

}
