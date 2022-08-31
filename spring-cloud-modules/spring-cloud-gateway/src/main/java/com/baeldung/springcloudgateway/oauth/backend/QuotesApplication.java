/**
 * 
 */
package com.baeldung.springcloudgateway.oauth.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.oauth2.server.resource.introspection.NimbusReactiveOpaqueTokenIntrospector;
import org.springframework.security.oauth2.server.resource.introspection.ReactiveOpaqueTokenIntrospector;

import com.baeldung.springcloudgateway.oauth.shared.KeycloakReactiveTokenInstrospector;

/**
 * @author Philippe
 *
 */
@SpringBootApplication
@PropertySource("classpath:quotes-application.properties")
@EnableWebFluxSecurity
public class QuotesApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(QuotesApplication.class);
    }
    
    
    @Bean
    public ReactiveOpaqueTokenIntrospector keycloakIntrospector(OAuth2ResourceServerProperties props) {
        
        NimbusReactiveOpaqueTokenIntrospector delegate = new NimbusReactiveOpaqueTokenIntrospector(
           props.getOpaquetoken().getIntrospectionUri(),
           props.getOpaquetoken().getClientId(),
           props.getOpaquetoken().getClientSecret());
        
        return new KeycloakReactiveTokenInstrospector(delegate);
    }
    
    
}

