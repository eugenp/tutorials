package com.baeldung.spring;

import org.springframework.context.annotation.ComponentScan;

// @Configuration
// @ImportResource({ "classpath:webSecurityConfig.xml" })
@ComponentScan("com.baeldung.security")
public class SecurityXmlConfig {

    public SecurityXmlConfig() {
        super();
    }

}
