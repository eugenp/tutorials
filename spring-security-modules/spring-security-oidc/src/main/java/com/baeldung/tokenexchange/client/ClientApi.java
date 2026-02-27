package com.baeldung.tokenexchange.client;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
@RequestMapping("/")
public class ClientApi {
    private static final String TARGET_RESOURCE_SERVER_URL = "http://localhost:8081/user/message";
    private final RestClient restClient;

    public ClientApi(RestClient restClient) {
        this.restClient = restClient;
    }

    @GetMapping
    public String index(@AuthenticationPrincipal OidcUser user) {
        return "<html><body><title>Hello</title><p>Hello '" +
          user.getSubject() + " (" + user.getAuthorizedParty() + ")" +
          "' from the Token Exchange Client!</p></br><p>" +
          "Use the <a href=\"/client/api/hello\">/api/hello</a> endpoint to access the resource server.</p></body></html>";
    }

    @GetMapping("/api/hello")
    public String hello(@RegisteredOAuth2AuthorizedClient(registrationId = "messaging-client-oidc") OAuth2AuthorizedClient oauth2AuthorizedClient) {
        RestClient.ResponseSpec responseSpec = restClient.get().uri(TARGET_RESOURCE_SERVER_URL)
          .headers(headers -> headers.setBearerAuth(oauth2AuthorizedClient.getAccessToken().getTokenValue()))
          .retrieve();

        String messageFromResourceServer = responseSpec.toEntity(String.class).getBody();
        return "<html><body><title>Token Exchange</title><p>Token Exchange Client!</p></br><p>" +
          "The resource server: <strong>" + messageFromResourceServer + "</strong></p></body></html>";
    }
}
