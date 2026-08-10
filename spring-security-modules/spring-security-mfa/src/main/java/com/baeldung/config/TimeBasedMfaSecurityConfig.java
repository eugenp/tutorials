package com.baeldung.config;
import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authorization.AuthorizationManagerFactory;
import org.springframework.security.authorization.AuthorizationManagerFactories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class TimeBasedMfaSecurityConfig {

    @Bean
    @Order(2)
    SecurityFilterChain profileSecurityFilterChain(HttpSecurity http) throws Exception {

        AuthorizationManagerFactory<Object> recentLogin =
                AuthorizationManagerFactories
                        .multiFactor()
                        .requireFactor(
                                factor ->
                                        factor
                                                .passwordAuthority()
                                                .validDuration(Duration.ofMinutes(5))
                        )
                        .build();

        http
                .securityMatcher("/profile", "/profile/**")
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers("/profile", "/profile/**")
                                .access(recentLogin.authenticated())
                                .anyRequest()
                                .authenticated()
                )
                .formLogin(withDefaults());

        return http.build();
    }

}