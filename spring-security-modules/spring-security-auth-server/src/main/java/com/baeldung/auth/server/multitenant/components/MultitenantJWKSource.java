package com.baeldung.auth.server.multitenant.components;

import com.nimbusds.jose.KeySourceException;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSelector;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.security.oauth2.server.authorization.context.AuthorizationServerContext;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class MultitenantJWKSource extends AbstractMultitenantComponent<JWKSource<SecurityContext>> implements JWKSource<SecurityContext> {

    public MultitenantJWKSource(Map<String,JWKSource<SecurityContext>> jwkSourceByTenant, Supplier<AuthorizationServerContext> authorizationServerContextSupplier) {
        super(jwkSourceByTenant,authorizationServerContextSupplier);
    }

    @Override
    public List<JWK> get(JWKSelector jwkSelector, SecurityContext securityContext) throws KeySourceException {

        var opt = getComponent();

        if (opt.isEmpty()) {
            return List.of();
        }

        return opt.get().get(jwkSelector,securityContext);
    }
}
