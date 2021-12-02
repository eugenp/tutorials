package com.baeldung.annotations.websecurity;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class CustomWebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
          .authorizeRequests()
          .antMatchers("/admin/**")
          .hasRole("ADMIN")
          .antMatchers("/protected/**")
          .hasRole("USER");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
          .ignoring()
          .antMatchers("/public/*");
    }
}
