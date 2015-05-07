package org.baeldung.security;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.stereotype.Controller;

/**
 * Security Configuration - LDAP and HTTP Authorizations.
 */
@EnableAutoConfiguration
@ComponentScan
@Controller
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.ldapAuthentication().userSearchBase("ou=people").userSearchFilter("(uid={0})").groupSearchBase("ou=groups").groupSearchFilter("member={0}").contextSource().root("dc=baeldung,dc=com").ldif("classpath:users.ldif");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/", "/home").permitAll().anyRequest().authenticated();
        http.formLogin().loginPage("/login").permitAll().and().logout().logoutSuccessUrl("/");
    }

}
