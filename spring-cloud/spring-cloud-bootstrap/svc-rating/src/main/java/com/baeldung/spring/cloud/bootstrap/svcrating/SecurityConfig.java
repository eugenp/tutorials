package com.baeldung.spring.cloud.bootstrap.svcrating;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal1(AuthenticationManagerBuilder auth) throws Exception {
        //try in memory auth with no users to support the case that this will allow for users that are logged in to go anywhere
        auth.inMemoryAuthentication();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        .authorizeRequests()
            .regexMatchers("^/ratings\\?bookId.*$").authenticated()
            .antMatchers(HttpMethod.POST,"/ratings").authenticated()
            .antMatchers(HttpMethod.PATCH,"/ratings/*").hasRole("ADMIN")
            .antMatchers(HttpMethod.DELETE,"/ratings/*").hasRole("ADMIN")
            .antMatchers(HttpMethod.GET,"/ratings").hasRole("ADMIN")
            .antMatchers(HttpMethod.GET,"/hystrix").authenticated()
            .anyRequest().authenticated()
            .and()
         .httpBasic().and()   
        .csrf()
            .disable();
       
        
    }
}
