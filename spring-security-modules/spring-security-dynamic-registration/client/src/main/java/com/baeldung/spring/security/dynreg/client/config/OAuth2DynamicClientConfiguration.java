package com.baeldung.spring.security.dynreg.client.config;

import com.baeldung.spring.security.dynreg.client.service.DynamicClientRegistrationRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientPropertiesMapper;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestCustomizers;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.web.SecurityFilterChain;

import java.net.URI;
import java.util.List;
import java.util.Map;

@Configuration
@EnableConfigurationProperties({ OAuth2DynamicClientConfiguration.RegistrationProperties.class, OAuth2ClientProperties.class })
@Slf4j
@RequiredArgsConstructor
public class OAuth2DynamicClientConfiguration {

    private final OAuth2ClientProperties clientProperties;
    private final RegistrationProperties registrationProperties;

    @Bean
    ClientRegistrationRepository dynamicClientRegistrationRepository( DynamicClientRegistrationRepository.RegistrationRestTemplate restTemplate) {

        log.info("Creating a dynamic client registration repository");

        var registrationDetails = new DynamicClientRegistrationRepository.RegistrationDetails(
          registrationProperties.getRegistrationEndpoint(),
          registrationProperties.getRegistrationUsername(),
          registrationProperties.getRegistrationPassword(),
          registrationProperties.getRegistrationScopes(),
          registrationProperties.getGrantTypes(),
          registrationProperties.getRedirectUris(),
          registrationProperties.getTokenEndpoint());

        // Use standard client registrations as
        Map<String,ClientRegistration> staticClients = (new OAuth2ClientPropertiesMapper(clientProperties)).asClientRegistrations();
        var repo =  new DynamicClientRegistrationRepository(registrationDetails, staticClients, restTemplate);
        repo.doRegistrations();
        return repo;
    }


    @Bean
    DynamicClientRegistrationRepository.RegistrationRestTemplate registrationRestTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.build(DynamicClientRegistrationRepository.RegistrationRestTemplate.class);
    }


    // As of Spring Boot 3.2, we could use a record instead of a class.
    @ConfigurationProperties(prefix = "baeldung.security.client.registration")
    @Getter
    @Setter
    public static final class RegistrationProperties {
        URI registrationEndpoint;
        String registrationUsername;
        String registrationPassword;
        List<String> registrationScopes;
        List<String> grantTypes;
        List<String> redirectUris;
        URI tokenEndpoint;
    }

    @Bean
    public OAuth2AuthorizationRequestResolver pkceResolver(ClientRegistrationRepository repo) {
        var resolver = new DefaultOAuth2AuthorizationRequestResolver(repo, "/oauth2/authorization");
        resolver.setAuthorizationRequestCustomizer(OAuth2AuthorizationRequestCustomizers.withPkce());
        return resolver;
    }

    @Bean
    SecurityFilterChain oauth2SecurityFilterChain(HttpSecurity http, OAuth2AuthorizationRequestResolver resolver) throws Exception {
        http.authorizeHttpRequests((requests) -> {
            requests.anyRequest().authenticated();
        });
        http.oauth2Login(a -> a.authorizationEndpoint(c -> c.authorizationRequestResolver(resolver))) ;
        http.oauth2Client(Customizer.withDefaults());
        return http.build();
    }


}
