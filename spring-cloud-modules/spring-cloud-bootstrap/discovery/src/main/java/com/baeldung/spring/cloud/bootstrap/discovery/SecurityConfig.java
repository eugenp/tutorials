package com.baeldung.spring.cloud.bootstrap.discovery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
@Order(1)
public class SecurityConfig  {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("discUser").password("{noop}discPassword").roles("SYSTEM");
    }

    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS)).authorizeHttpRequests(auth -> auth.requestMatchers("/eureka/**")).authorizeRequests(auth -> auth.requestMatchers("/eureka/**").hasRole("SYSTEM").anyRequest().denyAll()).httpBasic(
                Customizer.withDefaults()).csrf(csrf -> csrf.disable());
    }

    @Configuration
    // no order tag means this is the last security filter to be evaluated
    public static class AdminSecurityConfig {

        public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
            auth.inMemoryAuthentication();
        }

        protected void configure(HttpSecurity http) throws Exception {
            http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.NEVER)).httpBasic(basic -> basic.disable()).authorizeRequests().requestMatchers(HttpMethod.GET, "/").hasRole("ADMIN").requestMatchers("/info", "/health").authenticated().anyRequest().denyAll()
                    .and().csrf(csrf -> csrf.disable());
        }
    }
}
