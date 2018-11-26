package com.baeldung.oauth2extractors.configuration;

import com.baeldung.oauth2extractors.extractor.custom.BaeldungAuthoritiesExtractor;
import com.baeldung.oauth2extractors.extractor.custom.BaeldungPrincipalExtractor;
import com.baeldung.oauth2extractors.extractor.github.GithubAuthoritiesExtractor;
import com.baeldung.oauth2extractors.extractor.github.GithubPrincipalExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
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
    @Profile("oauth2-extractors-baeldung")
    public PrincipalExtractor baeldungPrincipalExtractor() {
        return new BaeldungPrincipalExtractor();
    }

    @Bean
    @Profile("oauth2-extractors-baeldung")
    public AuthoritiesExtractor baeldungAuthoritiesExtractor() {
        return new BaeldungAuthoritiesExtractor();
    }

    @Bean
    @Profile("oauth2-extractors-github")
    public PrincipalExtractor githubPrincipalExtractor() {
        return new GithubPrincipalExtractor();
    }

    @Bean
    @Profile("oauth2-extractors-github")
    public AuthoritiesExtractor githubAuthoritiesExtractor() {
        return new GithubAuthoritiesExtractor();
    }
}