package com.baeldung.multitenant.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
          .passwordEncoder(passwordEncoder())
          .withUser("user")
          .password(passwordEncoder().encode("baeldung"))
          .roles("tenant_1");

        auth.inMemoryAuthentication()
          .passwordEncoder(passwordEncoder())
          .withUser("admin")
          .password(passwordEncoder().encode("baeldung"))
          .roles("tenant_2");
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
          .authorizeRequests()
          .antMatchers("/login").permitAll()
          .anyRequest().authenticated()
          .and()
          .sessionManagement()
          .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
          .and()
          .addFilterBefore(new LoginFilter("/login", authenticationManager()),
            UsernamePasswordAuthenticationFilter.class)
          .addFilterBefore(new AuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
          .csrf().disable()
          .headers().frameOptions().disable()
          .and()
          .httpBasic();
    }
}
