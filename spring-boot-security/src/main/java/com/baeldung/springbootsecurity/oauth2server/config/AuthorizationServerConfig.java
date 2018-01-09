package com.baeldung.springbootsecurity.oauth2server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;

@Configuration
@Profile("authz")
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients
          .inMemory()
          .withClient("baeldung")
          .secret("baeldung")
          .authorizedGrantTypes("client_credentials", "password", "authorization_code")
          .scopes("openid", "read")
          .autoApprove(true)
          .and()
          .withClient("baeldung-admin")
          .secret("baeldung")
          .authorizedGrantTypes("authorization_code", "client_credentials", "refresh_token")
          .scopes("read", "write")
          .autoApprove(true);
    }
}
