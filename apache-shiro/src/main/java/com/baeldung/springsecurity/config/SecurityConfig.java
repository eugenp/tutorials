package com.baeldung.springsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests(authorize -> authorize.antMatchers("/index", "/login")
            .permitAll()
            .antMatchers("/home", "/logout")
            .authenticated()
            .antMatchers("/admin/**")
            .hasRole("ADMIN"))
            .formLogin(formLogin -> formLogin.loginPage("/login")
                .failureUrl("/login-error"));
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
            .withUser("Jerry")
            .password(passwordEncoder().encode("password"))
            .authorities("READ", "WRITE")
            .roles("ADMIN")
            .and()
            .withUser("Tom")
            .password(passwordEncoder().encode("password"))
            .authorities("READ")
            .roles("USER");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
