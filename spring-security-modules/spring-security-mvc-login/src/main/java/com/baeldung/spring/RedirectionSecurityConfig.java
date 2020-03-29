package com.baeldung.spring;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

//@Configuration
//@ImportResource({ "classpath:RedirectionWebSecurityConfig.xml" })
//@EnableWebSecurity
//@Profile("!https")
public class RedirectionSecurityConfig extends WebSecurityConfigurerAdapter {

    public RedirectionSecurityConfig() {
        super();
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
            .withUser("user1")
            .password("user1Pass")
            .roles("USER");
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/login*")
            .permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .formLogin()
            .successHandler(new SavedRequestAwareAuthenticationSuccessHandler());
        // .successHandler(new RefererAuthenticationSuccessHandler())
    }

}
