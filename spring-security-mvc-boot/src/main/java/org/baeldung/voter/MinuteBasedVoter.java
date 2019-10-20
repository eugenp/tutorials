package org.baeldung.voter;

import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

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
        return authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).filter(r -> "ROLE_USER".equals(r) && LocalDateTime.now().getMinute() % 2 != 0).findAny().map(s -> ACCESS_DENIED).orElseGet(() -> ACCESS_ABSTAIN);
    }
}
