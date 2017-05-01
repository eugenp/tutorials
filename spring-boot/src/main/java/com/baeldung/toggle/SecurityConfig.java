package com.baeldung.toggle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        //@formatter:off
        auth.inMemoryAuthentication()
          .withUser("user").password("pass").roles("USER")
          .and()
          .withUser("admin").password("pass").roles("ADMIN");
        //@formatter:on
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        //@formatter:off
        http.authorizeRequests().antMatchers("/increaseSalary").permitAll()
          .and()
          .csrf().disable()
          .httpBasic();
        //@formatter:on
    }
}
