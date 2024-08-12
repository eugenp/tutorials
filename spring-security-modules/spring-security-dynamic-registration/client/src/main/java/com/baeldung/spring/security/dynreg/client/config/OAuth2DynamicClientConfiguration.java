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
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

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
}
