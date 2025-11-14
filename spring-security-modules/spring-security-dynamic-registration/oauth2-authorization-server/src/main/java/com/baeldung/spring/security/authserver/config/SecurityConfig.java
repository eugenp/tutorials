package com.baeldung.spring.security.authserver.config;

import com.baeldung.spring.security.authserver.repository.CustomRegisteredClientRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;

import java.util.UUID;

@Configuration
@EnableConfigurationProperties(SecurityConfig.RegistrationProperties.class)
public class SecurityConfig {

    @Bean
    @Order(1)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
        http.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
          .oidc(oidc -> {
              oidc.clientRegistrationEndpoint(Customizer.withDefaults());
          });

        // Redirect to login page when user not authenticated
        http.exceptionHandling((exceptions) -> exceptions
          .defaultAuthenticationEntryPointFor(
            new LoginUrlAuthenticationEntryPoint("/login"),
            new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
          )
        );

        // Accept access tokens for User Info and/or Client Registration
        http.oauth2ResourceServer((resourceServer) -> resourceServer
            .jwt(Customizer.withDefaults()));

        return http.build();
    }

    @Bean
    @Order(2)
    SecurityFilterChain loginFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests( r -> r.anyRequest().authenticated())
          .formLogin(Customizer.withDefaults())
          .build();
    }

    @Bean
    public RegisteredClientRepository registeredClientRepository(RegistrationProperties props) {
        RegisteredClient registrarClient = RegisteredClient.withId(UUID.randomUUID().toString())
          .clientId(props.getRegistrarClientId())
          .clientSecret(props.getRegistrarClientSecret())
          .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
          .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
          .clientSettings(ClientSettings.builder()
            .requireProofKey(false)
            .requireAuthorizationConsent(false)
            .build())
          .scope("client.create")
          .scope("client.read")
          .build();

        RegisteredClientRepository delegate = new  InMemoryRegisteredClientRepository(registrarClient);
        return new CustomRegisteredClientRepository(delegate);
    }

    @ConfigurationProperties(prefix = "baeldung.security.server.registration")
    @Getter
    @Setter
    public static class RegistrationProperties {
        private String registrarClientId;
        private String registrarClientSecret;

    }
}
