package org.baeldung.security;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@Configuration
@EnableWebSecurity
public class ApplicationConfig extends WebSecurityConfigurerAdapter {
    public static final String DEFAULT_PASSWORD = "password";

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        auth.inMemoryAuthentication()
            .passwordEncoder(encoder)
            .withUser("testUser")
            .password(encoder.encode(DEFAULT_PASSWORD))
            .roles("ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf();

        http.authorizeRequests()
            .antMatchers("/**")
            .permitAll()
            .and()
            .httpBasic();
    }
}
