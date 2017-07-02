package org.baeldung.multipleauthproviders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class MultipleAuthProvidersSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    CustomAuthenticationProvider customAuthProvider;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.authenticationProvider(customAuthProvider);

        auth.inMemoryAuthentication()
            .withUser("memuser")
            .password("pass")
            .roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic()
            .and()
            .authorizeRequests()
            .antMatchers("/api/**")
            .authenticated();
    }
}
