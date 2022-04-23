package com.baeldung.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        
        auth.inMemoryAuthentication()
          .withUser("admin").password(encoder.encode("admin")).roles("USER", "ADMIN")
          .and()
          .withUser("user1").password(encoder.encode("password1")).roles("USER")
          .and()
          .withUser("user2").password(encoder.encode("password2")).roles("USER")
          .and()
          .withUser("user3").password(encoder.encode("password3")).roles("USER")
          .and()
          .withUser("user4").password(encoder.encode("password4")).roles("USER")
          .and()
          .withUser("user5").password(encoder.encode("password5")).roles("USER")
          .and()
          .withUser("user6").password(encoder.encode("password6")).roles("USER")
          .and()
          .withUser("user7").password(encoder.encode("password7")).roles("USER")
          .and()
          .withUser("user8").password(encoder.encode("password8")).roles("USER")
          .and()
          .withUser("user9").password(encoder.encode("password9")).roles("USER")
          .and()
          .withUser("user10").password(encoder.encode("password10")).roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        
        http
          .authorizeRequests()
          .antMatchers("/secured/**").authenticated()        
          .anyRequest().permitAll()
          .and()
          .httpBasic();
    }    
}
