package com.baeldung.tokenexchange.userservice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
public class UserController {

    private final RestClient restClient;
//    private final OAuth2AuthorizedClientManager clientManager;

   /* public UserController(RestClient restClient, OAuth2AuthorizedClientManager clientManager) {
        this.restClient = restClient;
        this.clientManager = clientManager;
    }
*/
    @GetMapping("/user")
    public String getUser(Authentication authentication){
//        return "baeldung";
        return restClient.get()
            .uri("/message")
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + ((JwtAuthenticationToken) authentication).getToken().getTokenValue())
            .retrieve()
            .body(String.class);
    }

   /* @GetMapping("/messages")
    public String getUserMessages(Authentication authentication) {

        OAuth2AuthorizeRequest authorizeRequest =
          OAuth2AuthorizeRequest.withClientRegistrationId("token-exchange-client")
            .principal(authentication)
            .build();

        OAuth2AuthorizedClient authorizedClient = clientManager.authorize(authorizeRequest);

        if (authorizedClient == null) {
            throw new IllegalStateException("Token exchange failed");
        }

        String exchangedToken = authorizedClient
            .getAccessToken()
            .getTokenValue();

        return restClient.get()
            .uri("/messages")
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + exchangedToken)
            .retrieve()
            .body(String.class);
    }*/

    public UserController(RestClient restClient) {
        this.restClient = restClient;
    }

    @GetMapping(value = "/user/messages")
    public List<String> getMessagesWithImpersonation(
        @RegisteredOAuth2AuthorizedClient("my-token-exchange-client")
        OAuth2AuthorizedClient authorizedClient) {
        return getUserMessages(authorizedClient);
    }

    private List<String> getUserMessages(OAuth2AuthorizedClient authorizedClient) {
        // @formatter:off
        String[] messages = Objects.requireNonNull(
            this.restClient.get()
                .uri("/messages")
                .headers((headers) -> headers.setBearerAuth(authorizedClient.getAccessToken().getTokenValue()))
                .retrieve()
                .body(String[].class)
        );
        // @formatter:on

        List<String> userMessages = new ArrayList<>(Arrays.asList(messages));
        userMessages.add("%s has %d unread messages".formatted(authorizedClient.getPrincipalName(), messages.length));

        return userMessages;
    }

}
