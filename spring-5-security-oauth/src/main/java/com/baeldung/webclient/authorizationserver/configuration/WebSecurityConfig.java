package com.baeldung.webclient.authorizationserver.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    // ... more configuration, e.g. for form login
    // For example: Use only Http Basic and not form login.
    // http.authorizeRequests()
    // .anyRequest()
    // .authenticated()
    // .and()
    // .httpBasic();
        http.authorizeRequests()
            .antMatchers("/login", "/user/bael-user")
            .permitAll()
            .and()
            .authorizeRequests()
            .anyRequest()
            .authenticated().and()
        .formLogin()                      
            .and()
        .httpBasic();
    }
}
