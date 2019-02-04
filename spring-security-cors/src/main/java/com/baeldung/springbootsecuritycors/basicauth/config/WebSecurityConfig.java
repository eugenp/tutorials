package com.baeldung.springbootsecuritycors.basicauth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .inMemoryAuthentication()
                .withUser("user")
                .password("{noop}password")
                .roles("USER");
    }

@Override
protected void configure(HttpSecurity http) throws Exception {
    http
        .csrf().disable()
        .cors().and() //disable this line to reproduce the CORS 401
        .authorizeRequests()
            .anyRequest()
            .authenticated()
            .and()
        .httpBasic();
}
}
