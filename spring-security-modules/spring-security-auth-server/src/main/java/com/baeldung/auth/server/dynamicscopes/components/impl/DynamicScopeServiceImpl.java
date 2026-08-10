package com.baeldung.auth.server.dynamicscopes.components.impl;

import com.baeldung.auth.server.dynamicscopes.components.DynamicScopeService;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class DynamicScopeServiceImpl implements DynamicScopeService {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(DynamicScopeServiceImpl.class);
    @Override
    public boolean validate(String clientId, Set<String> scopes) {
        // Any scope starting with TX: is valid
        return scopes.stream()
          .filter(scope -> scope.toUpperCase().startsWith("TX:"))
          .map(scope -> true)
          .findFirst()
          .orElse(false);
    }

    @Override
    public boolean isConsentNeeded(String clientId, Set<String> requestedScopes) {
        log.debug("isConsentNeeded: clientId={}, requestedStopes={}",clientId, requestedScopes);
        return true;
    }
}
