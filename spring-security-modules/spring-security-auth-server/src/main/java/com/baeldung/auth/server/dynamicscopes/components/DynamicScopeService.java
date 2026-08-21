package com.baeldung.auth.server.dynamicscopes.components;

import java.util.Set;

public interface DynamicScopeService {

    boolean validate(String clientId, Set<String> scopes);

    boolean isConsentNeeded(String clientId, Set<String> requestedScopes);
}
