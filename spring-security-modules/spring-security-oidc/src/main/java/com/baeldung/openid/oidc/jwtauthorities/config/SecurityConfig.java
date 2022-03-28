package com.baeldung.openid.oidc.jwtauthorities.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.util.StringUtils;

@Configuration
@EnableConfigurationProperties(JwtMappingProperties.class)
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter(JwtMappingProperties mappingProps) {
        
        JwtGrantedAuthoritiesConverter converter = new JwtGrantedAuthoritiesConverter();
        if (StringUtils.hasText(mappingProps.getAuthoritiesPrefix())) {
            converter.setAuthorityPrefix(mappingProps.getAuthoritiesPrefix().trim());
        }
        
        if ( StringUtils.hasText(mappingProps.getAuthoritiesClaimName())) {
            converter.setAuthoritiesClaimName(mappingProps.getAuthoritiesClaimName());
        }
        
        return converter;
        
    }
    
    @Bean
    public JwtAuthenticationConverter customJwtAuthenticationConverter(JwtMappingProperties mappingProps, JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter) {
        
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        
        if ( StringUtils.hasText(mappingProps.getPrincipalClaimName())) {
            converter.setPrincipalClaimName(mappingProps.getPrincipalClaimName());
        }
        
        return converter;
    }
    
    @Bean
    SecurityFilterChain customJwtSecurityChain(HttpSecurity http, JwtAuthenticationConverter converter, JwtMappingProperties mappingProps) throws Exception {
        
        return http
            .authorizeRequests(auth -> {
                auth.antMatchers("/user/**")
                  .hasAuthority(mappingProps.getAuthoritiesClaimName() + "profile");
            })
            .oauth2ResourceServer(oauth2 -> {
            oauth2.jwt().jwtAuthenticationConverter(converter);
        })
        .build();
    }
    
    
    static class CustomJwtAuthenticationConverterr extends JwtAuthenticationConverter {
        
    }
    
}
