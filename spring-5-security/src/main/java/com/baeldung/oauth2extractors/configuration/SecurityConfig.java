package com.baeldung.oauth2extractors.configuration;

import com.baeldung.oauth2extractors.extractor.CustomAuthoritiesExtractor;
import com.baeldung.oauth2extractors.extractor.CustomPrincipalExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@PropertySource("application-oauth2-extractors.properties")
@EnableOAuth2Sso
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/login**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin().disable();
    }

    @Bean
    public PrincipalExtractor principalExtractor() {
        return new CustomPrincipalExtractor();
    }

    @Bean
    public AuthoritiesExtractor authoritiesExtractor() {
        return new CustomAuthoritiesExtractor();
    }
}