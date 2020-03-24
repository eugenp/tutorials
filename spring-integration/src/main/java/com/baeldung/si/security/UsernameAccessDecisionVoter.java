package com.baeldung.si.security;

import java.util.Collection;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;

public class UsernameAccessDecisionVoter implements AccessDecisionVoter<Object> {
    private String rolePrefix = "ROLE_";

    @Override
    public boolean supports(ConfigAttribute attribute) {
        if ((attribute.getAttribute() != null)
                && !attribute.getAttribute().startsWith(rolePrefix)) {
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) {
        if (authentication == null) {
            return ACCESS_DENIED;
        }
        String name = authentication.getName();
        int result = ACCESS_ABSTAIN;
        for (ConfigAttribute attribute : attributes) {
            if (this.supports(attribute)) {
                result = ACCESS_DENIED;
                if (attribute.getAttribute().equals(name)) {
                    return ACCESS_GRANTED;
                }
            }
        }
        return result;
    }

}
