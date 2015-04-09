package org.baeldung.SpringDataAuditDemo.audit;

import org.baeldung.SpringDataAuditDemo.model.User;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * This bean is used by {@link CreatedBy} and {@link LastModifiedBy} to audit currently authenticated user. It is registered in jpaConfig.xml.
 */
public class AuditorAwareImpl implements AuditorAware<User> {

    @Override
    public User getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        // principal is set by programmatically authenticating user in org.baeldung.SpringDataAuditDemo.AuditorTest
        return (User) authentication.getPrincipal();
    }
}
