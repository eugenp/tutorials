package com.baeldung.spring.security.authserver.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;

import java.util.Set;

@Slf4j
@RequiredArgsConstructor
public class CustomRegisteredClientRepository implements RegisteredClientRepository {

    private final RegisteredClientRepository delegate;

    @Override
    public void save(RegisteredClient registeredClient) {

        log.info("Saving registered client: id={}, name={}",
          registeredClient.getClientId(),
          registeredClient.getClientName());

        Set<String> scopes = ( registeredClient.getScopes() == null || registeredClient.getScopes().isEmpty())?
          Set.of("openid","email","profile"):
          registeredClient.getScopes();

        // Disable PKCE & Consent
        RegisteredClient modifiedClient = RegisteredClient.from(registeredClient)
          .scopes(s -> s.addAll(scopes))
          .clientSettings(ClientSettings
            .withSettings(registeredClient.getClientSettings().getSettings())
            .requireAuthorizationConsent(false)
            .requireProofKey(false)
            .build())
          .build();

        delegate.save(modifiedClient);
    }

    @Override
    public RegisteredClient findById(String id) {
        return delegate.findByClientId(id);
    }

    /**
     * Returns the registered client identified by the provided {@code clientId},
     * or {@code null} if not found.
     *
     * @param clientId
     *   the client identifier
     * @return the {@link RegisteredClient} if found, otherwise {@code null}
     */
    @Override
    public RegisteredClient findByClientId(String clientId) {
        return delegate.findByClientId(clientId);
    }
}
