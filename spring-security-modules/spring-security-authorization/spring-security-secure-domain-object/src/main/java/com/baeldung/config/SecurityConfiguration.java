package com.baeldung.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.method.AuthorizationAdvisorProxyFactory;
import org.springframework.security.authorization.method.AuthorizationAdvisorProxyFactory.TargetVisitor;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    @Bean
    SecurityFilterChain defaultSecurityFileterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.authorizeHttpRequests(authorizedRequest -> authorizedRequest.anyRequest()
            .authenticated())
            .csrf(Customizer.withDefaults())
            .build();
    }
    
    @Bean
    static Customizer<AuthorizationAdvisorProxyFactory> skipValueTypes() {
        return (factory) -> factory.setTargetVisitor(TargetVisitor.defaultsSkipValueTypes());
    }

    @Bean
    UserDetailsService userDetailsService() {

        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        UserDetails user = User.builder()
            .username("john")
            .password("password")
            .passwordEncoder(passwordEncoder::encode)
            .authorities("read")
            .build();

        return new InMemoryUserDetailsManager(user);
    }
}
