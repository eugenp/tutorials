package com.baeldung.springsecurity;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
          .inMemoryAuthentication()
          .withUser("user1")
          .password("user1Pass")
          .roles("USER")
          .and()
          .withUser("admin")
          .password("adminPass")
          .roles("ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
          .csrf()
          .disable()
          .authorizeRequests()
          .antMatchers("/auth/login*")
          .anonymous()
          .antMatchers("/home/admin*")
          .hasRole("ADMIN")
          .anyRequest()
          .authenticated()
          .and()
          .formLogin()
          .loginPage("/auth/login")
          .defaultSuccessUrl("/home", true)
          .failureUrl("/auth/login?error=true")
          .and()
          .logout()
          .logoutSuccessUrl("/auth/login");
    }
}