package com.baeldung.xss;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.XXssProtectionHeaderWriter;

@Configuration
public class SecurityConf {

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // Ignoring here is only for this example. Normally people would apply their own authentication/authorization policies
        return (web) -> web.ignoring()
            .requestMatchers("/**");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.headers(headers ->
            headers.xssProtection(
                xss -> xss.headerValue(XXssProtectionHeaderWriter.HeaderValue.ENABLED_MODE_BLOCK)
            ).contentSecurityPolicy(
                cps -> cps.policyDirectives("script-src 'self'")
            ));
        return http.build();
    }
}
