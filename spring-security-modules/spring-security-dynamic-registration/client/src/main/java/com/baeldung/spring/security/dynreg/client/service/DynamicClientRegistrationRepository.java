package com.baeldung.spring.security.dynreg.client.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
public class DynamicClientRegistrationRepository implements ClientRegistrationRepository, Iterable<ClientRegistration> {

    private final RegistrationDetails registrationDetails;
    private final Map<String, ClientRegistration> staticClients;
    private final RegistrationRestTemplate registrationClient;
    private final Map<String, ClientRegistration> registrations = new HashMap<>();

    @Override
    public ClientRegistration findByRegistrationId(String registrationId) {
        log.info("findByRegistrationId: {}", registrationId);
        return registrations.computeIfAbsent(registrationId, this::doRegistration);
    }

    private ClientRegistration doRegistration(String registrationId) {

        log.info("doRegistration: registrationId={}", registrationId);
        String token = createRegistrationToken();

        var staticRegistration = staticClients.get(registrationId);
        Assert.notNull(staticRegistration,"Invalid registrationId: " + registrationId);

        var body = Map.of(
          "client_name", staticRegistration.getClientName(),
          "grant_types", List.of(staticRegistration.getAuthorizationGrantType()),
          "scope", String.join(" ", staticRegistration.getScopes()),
          "redirect_uris", List.of(resolveCallbackUri(staticRegistration)));

        var headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        var request = new RequestEntity<>(
          body,
          headers,
          HttpMethod.POST,
          registrationDetails.registrationEndpoint());

        var response = registrationClient.exchange(request, ObjectNode.class);

        if ( response.getBody() == null ) {
            throw new RuntimeException("Invalid registration response");
        }

        return createClientRegistration(staticRegistration, response.getBody());

    }

    private String resolveCallbackUri(ClientRegistration registration) {

        var path = UriComponentsBuilder.fromUriString(registration.getRedirectUri())
          .build(Map.of(
            "baseUrl", "",
            "action", "login",
            "registrationId", registration.getRegistrationId()))
          .toString();

        return "http://localhost:8090" + path;
    }

    private ClientRegistration createClientRegistration(ClientRegistration staticRegistration, ObjectNode body) {

        var clientId = body.get("client_id").asText();
        var clientSecret = body.get("client_secret").asText();

        log.info("creating ClientRegistration: registrationId={}, client_id={}", staticRegistration.getRegistrationId(),clientId);

        return ClientRegistration.withClientRegistration(staticRegistration)
          .clientId(body.get("client_id").asText())
          .clientSecret(body.get("client_secret").asText())
          .build();

    }

    private String createRegistrationToken() {

        var body = new LinkedMultiValueMap<String,String>();
        body.put( "grant_type", List.of("client_credentials"));
        body.put(  "scope", registrationDetails.registrationScopes());

        var headers = new HttpHeaders();
        headers.setBasicAuth(registrationDetails.registrationUsername(), registrationDetails.registrationPassword());
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        var request = new RequestEntity<>(
          body,
          headers,
          HttpMethod.POST,
          registrationDetails.tokenEndpoint());

        var result = registrationClient.exchange(request, ObjectNode.class);
        if ( !result.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Failed to create registration token: code=" + result.getStatusCode());
        }

        return result.getBody().get("access_token").asText();
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<ClientRegistration> iterator() {
        return registrations
          .values()
          .iterator();
    }

    public void doRegistrations() {
        staticClients.forEach((key, value) -> findByRegistrationId(key));
    }

    public record RegistrationDetails(
      URI registrationEndpoint,
      String registrationUsername,
      String registrationPassword,
      List<String> registrationScopes,
      List<String> grantTypes,
      List<String> redirectUris,
      URI tokenEndpoint
      ) {
    }

    // Type-safe RestTemplate
    public static class RegistrationRestTemplate extends RestTemplate {
    }
}
