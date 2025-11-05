package com.baeldung.contentsecuritypolicy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.StaticHeadersWriter;

@Configuration
public class ContentSecurityPolicySecurityConfiguration {
    private static final String REPORT_TO = "{\"group\":\"csp-violation-report\",\"max_age\":2592000,\"endpoints\":[{\"url\":\"https://localhost:8080/report\"}]}";

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> authorizationManagerRequestMatcherRegistry.requestMatchers("/**").permitAll())
            .headers(httpSecurityHeadersConfigurer ->
                    httpSecurityHeadersConfigurer
                            .addHeaderWriter(new StaticHeadersWriter("Report-To", REPORT_TO))
                            .xssProtection(Customizer.withDefaults())
                            .contentSecurityPolicy(contentSecurityPolicyConfig ->
                                    contentSecurityPolicyConfig.policyDirectives("form-action 'self'; report-uri /report; report-to csp-violation-report")));
        return http.build();
    }
}
