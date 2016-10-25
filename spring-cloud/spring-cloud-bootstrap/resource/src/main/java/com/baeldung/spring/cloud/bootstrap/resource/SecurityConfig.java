package com.baeldung.spring.cloud.bootstrap.resource;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
                http.httpBasic().disable().authorizeRequests().antMatchers("/hello/cloud").permitAll().antMatchers("/hello/user").hasAnyRole("USER", "ADMIN").antMatchers("/hello/admin").hasRole("ADMIN").anyRequest().authenticated().and().csrf().disable();
        }
}
