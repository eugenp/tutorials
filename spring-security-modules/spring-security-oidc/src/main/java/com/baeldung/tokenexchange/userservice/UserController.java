package com.baeldung.tokenexchange.userservice;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
public class UserController {
    private static final String TARGET_RESOURCE_SERVER_URL = "http://localhost:8082/message";
    private final OAuth2AuthorizedClientManager authorizedClientManager;
    private final RestClient restClient;

    public UserController(OAuth2AuthorizedClientManager authorizedClientManager, RestClient restClient) {
        this.authorizedClientManager = authorizedClientManager;
        this.restClient = restClient;
    }

    @GetMapping("/user/message")
    public String message(JwtAuthenticationToken jwtAuthentication) {

        OAuth2AuthorizeRequest authorizeRequest = OAuth2AuthorizeRequest.withClientRegistrationId("my-token-exchange-client")
                .principal(jwtAuthentication)
                .build();

        OAuth2AuthorizedClient authorizedClient = this.authorizedClientManager.authorize(authorizeRequest);

        assert authorizedClient != null;

        OAuth2AccessToken accessToken = authorizedClient.getAccessToken();
        if (accessToken == null) {
            return "token exchange resource server";
        }

        RestClient.ResponseSpec responseSpec = restClient.get().uri(TARGET_RESOURCE_SERVER_URL)
          .headers(headers -> headers.setBearerAuth(accessToken.getTokenValue()))
          .retrieve();
        ResponseEntity<String> responseEntity = responseSpec.toEntity(String.class);
        return responseEntity.getBody();
    }
}