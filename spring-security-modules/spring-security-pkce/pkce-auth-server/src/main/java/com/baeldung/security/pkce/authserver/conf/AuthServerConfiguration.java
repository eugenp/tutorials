package com.baeldung.security.pkce.authserver.conf;

import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.authorization.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.ClientSettings;
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
public class AuthServerConfiguration {
    
    @Bean
    @Order(1)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
        
        var authorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer<HttpSecurity>();
        var endpointsMatcher = authorizationServerConfigurer.getEndpointsMatcher();

        // @formatter:off
        http
          .requestMatcher(endpointsMatcher)
            .authorizeRequests(authorize ->
                authorize              
                  .anyRequest()
                  .authenticated());
        http
          .exceptionHandling(exceptions -> 
              exceptions.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login")))
          .csrf( csrf -> 
              csrf
                .ignoringRequestMatchers(endpointsMatcher))
          .apply(authorizationServerConfigurer);
         
        // Required by /userinfo endpoint
        http.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
        return http.build();
        // @formatter:on
    }
    
    @Bean 
    @Order(2)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        // @formatter:off
        return http
          .formLogin(Customizer.withDefaults())
          .build(); 
        // @formatter:on
    }
        
    @Bean 
    public RegisteredClientRepository registeredClientRepository() {
                
        var pkceClient = RegisteredClient
            .withId(UUID.randomUUID().toString())
            .clientId("pkce-client")
            .clientSecret("{noop}obscura")
            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
            .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
            .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
            .scope(OidcScopes.OPENID)          
            .scope(OidcScopes.EMAIL)          
            .scope(OidcScopes.PROFILE)
            .clientSettings(ClientSettings.builder()
              .requireAuthorizationConsent(false)
              .requireProofKey(true)
              .build())
            .redirectUri("http://127.0.0.1:8080/login/oauth2/code/pkce") // Localhost not allowed
            .build();
        
        return new InMemoryRegisteredClientRepository(pkceClient);
    }
    
    @Bean 
    public ProviderSettings providerSettings() {
        return ProviderSettings
          .builder()
          .build();
    }

}
