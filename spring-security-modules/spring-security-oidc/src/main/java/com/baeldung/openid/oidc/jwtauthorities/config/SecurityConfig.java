package com.baeldung.openid.oidc.jwtauthorities.config;

import java.util.Collection;
import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.util.StringUtils;

import com.baeldung.openid.oidc.jwtauthorities.service.AccountService;

@Configuration
@EnableConfigurationProperties(JwtMappingProperties.class)
@EnableMethodSecurity
public class SecurityConfig {
    
    private final JwtMappingProperties mappingProps;
    private final AccountService accountService;
    
    public SecurityConfig(JwtMappingProperties mappingProps, AccountService accountService) {
        this.mappingProps = mappingProps;
        this.accountService = accountService;
    }

    @Bean
    public String jwtGrantedAuthoritiesPrefix() {
        return mappingProps.getAuthoritiesPrefix();
    }

    @Bean
    public Converter<Jwt, Collection<GrantedAuthority>> jwtGrantedAuthoritiesConverter() {
        MappingJwtGrantedAuthoritiesConverter converter = new MappingJwtGrantedAuthoritiesConverter(mappingProps.getScopes());

        if (StringUtils.hasText(mappingProps.getAuthoritiesPrefix())) {
            converter.setAuthorityPrefix(mappingProps.getAuthoritiesPrefix()
                .trim());
        }

        if (StringUtils.hasText(mappingProps.getAuthoritiesClaimName())) {
            converter.setAuthoritiesClaimName(mappingProps.getAuthoritiesClaimName());
        }
        return converter;
    }

    @Bean
    public Converter<Jwt,AbstractAuthenticationToken> customJwtAuthenticationConverter(AccountService accountService) {
        return new CustomJwtAuthenticationConverter(
          accountService,
          jwtGrantedAuthoritiesConverter(),
          mappingProps.getPrincipalClaimName());
    }

    @Bean
    SecurityFilterChain customJwtSecurityChain(HttpSecurity http) throws Exception {
        // @formatter:off
        return http.oauth2ResourceServer(oauth2 -> {
            oauth2.jwt()
              .jwtAuthenticationConverter(customJwtAuthenticationConverter(accountService));
        })
        .build();
        // @formatter:on
    }

}
