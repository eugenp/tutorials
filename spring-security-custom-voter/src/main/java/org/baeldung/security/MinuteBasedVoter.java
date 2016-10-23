package org.baeldung.security;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class MinuteBasedVoter implements AccessDecisionVoter {
    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class clazz) {
        return true;
    }

    @Override
    public int vote(Authentication authentication, Object object, Collection collection) {
        String role = authentication
                .getAuthorities()
                .stream().map(GrantedAuthority::getAuthority)
                .filter("ROLE_USER"::equals)
                .findAny()
                .orElseGet(() -> "ROLE_ADMIN");

        if ("ROLE_USER".equals(role) && LocalDateTime.now().getMinute() % 2 != 0) {
                return ACCESS_DENIED;
        }

        return ACCESS_ABSTAIN;
    }
}
