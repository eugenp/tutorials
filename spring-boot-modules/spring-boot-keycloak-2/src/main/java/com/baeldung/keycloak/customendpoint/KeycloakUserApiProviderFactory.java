package com.baeldung.keycloak.customendpoint;

import org.keycloak.Config.Scope;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.services.resource.RealmResourceProvider;
import org.keycloak.services.resource.RealmResourceProviderFactory;

public class KeycloakUserApiProviderFactory implements RealmResourceProviderFactory {
    public static final String ID = "users-by-group-and-role-name";

    @Override
    public RealmResourceProvider create(KeycloakSession session) {
        return new KeycloakUserApiProvider(session);
    }

    @Override
    public void init(Scope config) {
    }

    @Override
    public void postInit(KeycloakSessionFactory factory) {
    }

    @Override
    public void close() {
    }

    @Override
    public String getId() {
        return ID;
    }
}