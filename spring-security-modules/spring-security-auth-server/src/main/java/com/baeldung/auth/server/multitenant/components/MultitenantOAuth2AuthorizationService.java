package com.baeldung.auth.server.multitenant.components;

import org.jspecify.annotations.Nullable;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.context.AuthorizationServerContext;

import java.util.Map;
import java.util.function.Supplier;

public class MultitenantOAuth2AuthorizationService extends AbstractMultitenantComponent<OAuth2AuthorizationService> implements OAuth2AuthorizationService {

    public MultitenantOAuth2AuthorizationService(Map<String, OAuth2AuthorizationService> authorizationServiceByTenant, Supplier<AuthorizationServerContext> authorizationServerContextSupplier) {
        super(authorizationServiceByTenant,authorizationServerContextSupplier);
    }

    @Override
    public void save(OAuth2Authorization authorization) {
        getComponent().ifPresent(service -> service.save(authorization));
    }

    @Override
    public void remove(OAuth2Authorization authorization) {
        getComponent().ifPresent(service -> service.remove(authorization));
    }

    @Override
    public @Nullable OAuth2Authorization findById(String id) {
        return getComponent()
          .map(auth -> auth.findById(id))
          .orElse(null);
    }

    @Override
    public @Nullable OAuth2Authorization findByToken(String token, @Nullable OAuth2TokenType tokenType) {
        return getComponent()
          .map(auth -> auth.findByToken(token, tokenType))
          .orElse(null);
    }
}
