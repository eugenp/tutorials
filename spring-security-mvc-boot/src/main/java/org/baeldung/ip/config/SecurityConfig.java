package org.baeldung.ip.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity//(debug = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomIpAuthenticationProvider authenticationProvider;

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
       auth.inMemoryAuthentication().withUser("john").password("{noop}123").authorities("ROLE_USER");
     //   auth.authenticationProvider(authenticationProvider);
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        // @formatter:off
        http.authorizeRequests()
            .antMatchers("/login").permitAll()
//            .antMatchers("/foos/**").hasIpAddress("11.11.11.11")
            .antMatchers("/foos/**").access("isAuthenticated() and hasIpAddress('11.11.11.11')")
            .anyRequest().authenticated()
            .and().formLogin().permitAll()
            .and().csrf().disable();
        // @formatter:on
    }

}