package com.baeldung.OncePerRequestFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

@EnableWebSecurity
public class WebMvcConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public MyFilter myFilter(){
        return new MyFilter();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(myFilter(), AnonymousAuthenticationFilter.class)
                .authorizeRequests()
                .anyRequest().authenticated();
    }
}