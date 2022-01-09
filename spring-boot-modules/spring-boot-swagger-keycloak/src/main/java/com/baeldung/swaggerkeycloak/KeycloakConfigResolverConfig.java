package com.baeldung.swaggerkeycloak;

import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConfigResolverConfig {

    /*
     * re-configure keycloak adapter for Spring Boot environment,
     * i.e. to read config from application.yml
     * (otherwise, we need a keycloak.json file)
     */
    @Bean
    public KeycloakConfigResolver configResolver() {
        return new KeycloakSpringBootConfigResolver();
    }



}
