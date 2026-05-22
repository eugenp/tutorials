package com.baeldung.auth.server.dynamicscopes.components.impl;

import com.baeldung.auth.server.dynamicscopes.components.DynamicScopeService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class DynamicScopeServiceImpl implements DynamicScopeService {
    @Override
    public boolean validate(String clientId, Set<String> scopes) {
        // Any scope starting with TX: is valid
        return scopes.stream()
          .filter(scope -> scope.toUpperCase().startsWith("TX:"))
          .map(scope -> true)
          .findFirst()
          .orElse(false);
    }
}
