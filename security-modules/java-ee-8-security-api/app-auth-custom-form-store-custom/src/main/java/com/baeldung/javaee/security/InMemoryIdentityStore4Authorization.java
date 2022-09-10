package com.baeldung.javaee.security;

import javax.enterprise.context.ApplicationScoped;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;
import java.util.*;

@ApplicationScoped
class InMemoryIdentityStore4Authorization implements IdentityStore {

    private Map<String, List<String>> userRoles = new HashMap<>();

    public InMemoryIdentityStore4Authorization() {
        //Init users
        // from a file or hardcoded
        init();
    }

    private void init() {
        //user1
        List<String> roles = new ArrayList<>();
        roles.add("USER_ROLE");
        userRoles.put("user", roles);
        //user2
        roles = new ArrayList<>();
        roles.add("USER_ROLE");
        roles.add("ADMIN_ROLE");
        userRoles.put("admin", roles);
    }

    @Override
    public int priority() {
        return 80;
    }

    @Override
    public Set<ValidationType> validationTypes() {
        return EnumSet.of(ValidationType.PROVIDE_GROUPS);
    }

    @Override
    public Set<String> getCallerGroups(CredentialValidationResult validationResult) {
        List<String> roles = userRoles.get(validationResult.getCallerPrincipal().getName());
        return new HashSet<>(roles);
    }
}
