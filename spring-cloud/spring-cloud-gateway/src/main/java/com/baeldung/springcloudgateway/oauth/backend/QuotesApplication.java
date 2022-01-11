/**
 * 
 */
package com.baeldung.springcloudgateway.oauth.backend;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.server.resource.introspection.NimbusReactiveOpaqueTokenIntrospector;
import org.springframework.security.oauth2.server.resource.introspection.ReactiveOpaqueTokenIntrospector;
import org.springframework.security.web.server.SecurityWebFilterChain;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Philippe
 *
 */
@SpringBootApplication
@PropertySource("classpath:quotes-application.properties")
@EnableWebFluxSecurity
@Slf4j
public class QuotesApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(QuotesApplication.class);
    }
    
    
    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
            .authorizeExchange(exchanges -> exchanges
                .anyExchange().authenticated()
            )
            .oauth2ResourceServer(oauth2 -> oauth2
                .opaqueToken(opaqueToken -> opaqueToken
                    .introspector(customIntrospector())
                )
            );
        return http.build();
    }
    
    @Value("${spring.security.oauth2.resourceserver.opaquetoken.introspection-uri}")
    private String introspectionUri;
    
    @Value("${spring.security.oauth2.resourceserver.opaquetoken.client-id}")
    private String clientId;
    
    @Value("${spring.security.oauth2.resourceserver.opaquetoken.client-secret}")
    private String secretId;
    @Bean
    public ReactiveOpaqueTokenIntrospector customIntrospector() {
        log.info("[I58] >>> customIntrospector");
        return new NimbusReactiveOpaqueTokenIntrospector(introspectionUri, clientId, secretId);
    }    

}

