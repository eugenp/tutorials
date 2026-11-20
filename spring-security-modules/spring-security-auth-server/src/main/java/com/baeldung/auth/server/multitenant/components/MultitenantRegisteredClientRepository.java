package com.baeldung.auth.server.multitenant.components;

import org.jspecify.annotations.Nullable;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.context.AuthorizationServerContext;

import java.util.Map;
import java.util.function.Supplier;

public class MultitenantRegisteredClientRepository
  extends AbstractMultitenantComponent<RegisteredClientRepository>
  implements RegisteredClientRepository  {

    public MultitenantRegisteredClientRepository(Map<String, RegisteredClientRepository> clientRepoByTenant, Supplier<AuthorizationServerContext> authorizationServerContextSupplier) {
        super(clientRepoByTenant,authorizationServerContextSupplier);
    }

    @Override
    public void save(RegisteredClient registeredClient) {
         getComponent()
           .orElseThrow(UnknownIssuerException::new)
           .save(registeredClient);
    }

    @Override
    public @Nullable RegisteredClient findById(String id) {
        return getComponent()
          .map(repo -> repo.findById(id))
          .orElse(null);
    }

    @Override
    public @Nullable RegisteredClient findByClientId(String clientId) {
        return getComponent()
          .map(repo -> repo.findByClientId(clientId))
          .orElse(null);
    }

    private static class UnknownIssuerException extends RuntimeException {}
}
