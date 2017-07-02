package org.baeldung.spring;

import org.springframework.context.annotation.ComponentScan;

// @Configuration
// @ImportResource({ "classpath:webSecurityConfig.xml" })
@ComponentScan("org.baeldung.security")
class SecurityXmlConfig {

    public SecurityXmlConfig() {
        super();
    }

}
