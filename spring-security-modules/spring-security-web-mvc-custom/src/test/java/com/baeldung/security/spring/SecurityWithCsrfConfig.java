package com.baeldung.security.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityWithCsrfConfig extends WebSecurityConfigurerAdapter {

    public SecurityWithCsrfConfig() {
        super();
    }

    @Bean("authenticationManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        // @formatter:off
        auth
        .inMemoryAuthentication()
        .withUser("user1")
        .password("user1Pass")
        .authorities("ROLE_USER")
        .and()
        .withUser("admin")
        .password("adminPass")
        .authorities("ROLE_ADMIN");
        // @formatter:on
    }

    @Override
    public void configure(final WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        // @formatter:off
        http
        .authorizeRequests()
        .antMatchers("/auth/admin/*").hasAnyRole("ROLE_ADMIN")
        .anyRequest().authenticated()
        .and()
        .httpBasic()
        .and()
        .headers().cacheControl().disable();
        // @formatter:on
    }

}
