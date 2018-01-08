package com.baeldung.springbootsecurity.oauth2server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;

@Configuration
@Profile("custom")
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients
          .inMemory().withClient("baeldung").secret("baeldung")
          .authorizedGrantTypes("authorization_code", "client_credentials", "refresh_token", "password", "implicit")
          .scopes("openid", "read").autoApprove(true)
          .and().withClient("baeldung-admin").secret("baeldung")
          .authorizedGrantTypes("authorization_code", "client_credentials", "refresh_token", "password", "implicit")
          .scopes("read", "write").autoApprove(true);
    }
}
