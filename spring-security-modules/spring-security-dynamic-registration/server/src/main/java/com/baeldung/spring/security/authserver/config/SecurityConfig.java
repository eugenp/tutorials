package com.baeldung.spring.security.authserver.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import java.util.UUID;

@Configuration
@EnableConfigurationProperties(SecurityConfig.RegistrationProperties.class)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
        http.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
          .oidc(oidc -> {
              oidc.clientRegistrationEndpoint(Customizer.withDefaults());
          });

        http.oauth2ResourceServer(oauth2ResourceServer ->
          oauth2ResourceServer.jwt(Customizer.withDefaults()));

        return http.build();
    }

    @Bean
    public RegisteredClientRepository registeredClientRepository(RegistrationProperties props) {
        RegisteredClient registrarClient = RegisteredClient.withId(UUID.randomUUID().toString())
          .clientId(props.getRegistrarClientId())
          .clientSecret(props.getRegistrarClientSecret())
          .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
          .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
          .scope("client.create")
          .scope("client.read")
          .build();

        return new InMemoryRegisteredClientRepository(registrarClient);
    }

    @ConfigurationProperties(prefix = "baeldung.security.server.registration")
    @Getter
    @Setter
    public static class RegistrationProperties {
        private String registrarClientId;
        private String registrarClientSecret;

    }
}
