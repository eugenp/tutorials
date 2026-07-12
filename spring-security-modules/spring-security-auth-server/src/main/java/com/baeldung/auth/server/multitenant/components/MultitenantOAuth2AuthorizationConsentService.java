package com.baeldung.auth.server.multitenant.components;

import org.jspecify.annotations.Nullable;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.context.AuthorizationServerContext;

import java.util.Map;
import java.util.function.Supplier;

public class MultitenantOAuth2AuthorizationConsentService extends AbstractMultitenantComponent<OAuth2AuthorizationConsentService> implements OAuth2AuthorizationConsentService {


    public MultitenantOAuth2AuthorizationConsentService(Map<String,OAuth2AuthorizationConsentService> consentServiceByTenant, Supplier<AuthorizationServerContext> authorizationServerContextSupplier) {
        super(consentServiceByTenant,authorizationServerContextSupplier);
    }

    @Override
    public void save(OAuth2AuthorizationConsent authorizationConsent) {
        getComponent().ifPresent(service -> service.save(authorizationConsent));
    }

    @Override
    public void remove(OAuth2AuthorizationConsent authorizationConsent) {
        getComponent().ifPresent(service -> service.remove(authorizationConsent));
    }

    @Override
    public @Nullable OAuth2AuthorizationConsent findById(String registeredClientId, String principalName) {
        return getComponent()
          .map(service -> service.findById(registeredClientId, principalName))
          .orElse(null);
    }
}
