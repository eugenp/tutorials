package com.baeldung.integrationtesting;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class MethodSecurityConfigurer extends GlobalMethodSecurityConfiguration {

}
