package com.baeldung.openid.oidc.login.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;

@Configuration
public class OAuth2LoginSecurityConfig extends WebSecurityConfigurerAdapter {// @formatter:off

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        Set<String> googleScopes = new HashSet<>();
        googleScopes.add("https://www.googleapis.com/auth/userinfo.email");
        googleScopes.add("https://www.googleapis.com/auth/userinfo.profile");

        OidcUserService googleUserService = new OidcUserService();
        googleUserService.setAccessibleScopes(googleScopes);

        http.authorizeRequests(authorizeRequests -> authorizeRequests.anyRequest()
              .authenticated())
            .oauth2Login(oauthLogin -> oauthLogin.userInfoEndpoint()
              .oidcUserService(googleUserService));
    }// @formatter:on
}