package com.baeldung.openid.oidc.sessionmanagement.config;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@Configuration
public class OAuth2SessionManagementSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception { // @formatter:off
        http.authorizeRequests(authorizeRequests -> authorizeRequests.mvcMatchers("/home")
              .permitAll()
              .anyRequest()
                .authenticated())
            .oauth2Login(oauthLogin -> oauthLogin.permitAll())
            .logout(logout -> logout.logoutSuccessHandler(oidcLogoutSuccessHandler()));
    }  // @formatter:on


    private LogoutSuccessHandler oidcLogoutSuccessHandler() {
        OidcClientInitiatedLogoutSuccessHandler oidcLogoutSuccessHandler = new OidcClientInitiatedLogoutSuccessHandler(this.clientRegistrationRepository);

        oidcLogoutSuccessHandler.setPostLogoutRedirectUri(URI.create("http://localhost:8081/home"));

        return oidcLogoutSuccessHandler;
    }
}