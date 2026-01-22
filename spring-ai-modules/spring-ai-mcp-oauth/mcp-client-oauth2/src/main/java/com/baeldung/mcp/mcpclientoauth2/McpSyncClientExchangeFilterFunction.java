package com.baeldung.mcp.mcpclientoauth2;

import java.util.function.Consumer;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.client.ClientCredentialsOAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizationContext;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Component
public class McpSyncClientExchangeFilterFunction implements ExchangeFilterFunction {

    private final ClientCredentialsOAuth2AuthorizedClientProvider clientCredentialTokenProvider = new ClientCredentialsOAuth2AuthorizedClientProvider();

    private final ServletOAuth2AuthorizedClientExchangeFilterFunction delegate;

    private final ClientRegistrationRepository clientRegistrationRepository;

    private static final String AUTHORIZATION_CODE_CLIENT_REGISTRATION_ID = "authserver";

    private static final String CLIENT_CREDENTIALS_CLIENT_REGISTRATION_ID = "authserver-client-credentials";

    public McpSyncClientExchangeFilterFunction(OAuth2AuthorizedClientManager clientManager, ClientRegistrationRepository clientRegistrationRepository) {
        this.delegate = new ServletOAuth2AuthorizedClientExchangeFilterFunction(clientManager);
        this.delegate.setDefaultClientRegistrationId(AUTHORIZATION_CODE_CLIENT_REGISTRATION_ID);
        this.clientRegistrationRepository = clientRegistrationRepository;
    }

    @Override
    public Mono<ClientResponse> filter(ClientRequest request, ExchangeFunction next) {
        if (RequestContextHolder.getRequestAttributes() instanceof ServletRequestAttributes) {
            return this.delegate.filter(request, next);
        } else {
            var accessToken = getClientCredentialsAccessToken();
            var requestWithToken = ClientRequest.from(request)
                .headers(headers -> headers.setBearerAuth(accessToken))
                .build();
            return next.exchange(requestWithToken);
        }
    }

    private String getClientCredentialsAccessToken() {
        var clientRegistration = this.clientRegistrationRepository.findByRegistrationId(CLIENT_CREDENTIALS_CLIENT_REGISTRATION_ID);

        var authRequest = OAuth2AuthorizationContext.withClientRegistration(clientRegistration)
            .principal(new AnonymousAuthenticationToken("client-credentials-client", "client-credentials-client",
                AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS")))
            .build();
        return this.clientCredentialTokenProvider.authorize(authRequest)
            .getAccessToken()
            .getTokenValue();
    }

    public Consumer<WebClient.Builder> configuration() {
        return builder -> builder.defaultRequest(this.delegate.defaultRequest())
            .filter(this);
    }

}