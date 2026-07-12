package com.baeldung.auth.server.multitenant.components;

import org.springframework.security.oauth2.server.authorization.context.AuthorizationServerContext;

import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * Base class for multitenant components.
 * @param <T> the type of the component
 */
public class AbstractMultitenantComponent<T> {

    private Map<String,T> componentsByTenant;
    private Supplier<AuthorizationServerContext> authorizationServerContextSupplier;

    protected AbstractMultitenantComponent(Map<String,T> componentsByTenant, Supplier<AuthorizationServerContext> authorizationServerContextSupplier) {
        this.componentsByTenant = componentsByTenant;
        this.authorizationServerContextSupplier = authorizationServerContextSupplier;
    }

    protected Optional<T> getComponent() {

        var authorizationServerContext = authorizationServerContextSupplier.get();
        if ( authorizationServerContext == null || authorizationServerContext.getIssuer() == null ) {
            return Optional.empty();
        }

        var issuer = authorizationServerContext.getIssuer();
        for ( var entry : componentsByTenant.entrySet() ) {
            if ( issuer.endsWith(entry.getKey())) {
                return Optional.of(entry.getValue());
            }
        }

        return Optional.empty();
    }
}
