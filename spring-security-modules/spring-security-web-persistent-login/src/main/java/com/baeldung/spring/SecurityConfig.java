package com.baeldung.spring;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 * Spring Security Configuration.
 */
@Configuration
@EnableWebSecurity
@ImportResource({ "classpath:webSecurityConfig.xml" })
public class SecurityConfig {

    public SecurityConfig() {
        super();
    }

}
