package com.baeldung.cognito;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
            .and()
            .authorizeRequests(authz -> authz.mvcMatchers("/")
                .permitAll()
                .anyRequest()
                .authenticated())
            .oauth2Login()
            .and()
            .logout()
            .logoutSuccessUrl("/");
    }
}