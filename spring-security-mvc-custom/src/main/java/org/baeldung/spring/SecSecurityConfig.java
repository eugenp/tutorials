package org.baeldung.spring;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource({ "classpath:webSecurityConfig.xml" })
class SecSecurityConfig {

    public SecSecurityConfig() {
        super();
    }

}
