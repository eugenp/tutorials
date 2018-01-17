package com.cloudsecurity.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

@Configuration
@EnableWebSecurity
@EnableOAuth2Client
public class WebSecurityConfigurer
    extends
        WebSecurityConfigurerAdapter {

//    @Autowired
//    private OAuth2ClientContext oauth2ClientContext;

    @Override
    protected void configure(HttpSecurity http)
        throws Exception {
        http
            .authorizeRequests()
            .antMatchers("/login**").permitAll()
            .anyRequest().authenticated()
            .and().csrf()
            .and().formLogin().loginPage("/login");
    }

    @Override
    protected void configure(
        AuthenticationManagerBuilder auth) throws Exception {
        auth
            .inMemoryAuthentication()
            .withUser("user").password("user")
            .roles("USER")
            .and()
            .withUser("admin").password("admin")
            .roles("USER", "ADMIN");
    }

    @Override
    @Bean(name = "userDetailsService")
    public UserDetailsService userDetailsServiceBean()
        throws Exception {
        return super.userDetailsServiceBean();
    }


}