package com.baeldung.denyonmissing;

import static org.springframework.security.access.annotation.Jsr250SecurityConfig.DENY_ALL_ATTRIBUTE;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Component
public class CustomPermissionAllowedMethodSecurityMetadataSource implements AuthorizationManager<MethodInvocation> {

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, MethodInvocation mi) {
        MergedAnnotations annotations = MergedAnnotations.from(mi.getMethod(), MergedAnnotations.SearchStrategy.DIRECT);
        List<ConfigAttribute> attributes = new ArrayList<>();

        MergedAnnotations classAnnotations = MergedAnnotations.from(DenyOnMissingController.class,  MergedAnnotations.SearchStrategy.DIRECT);
        // if the class is annotated as @Controller we should by default deny access to every method
        if (classAnnotations.get(Controller.class).isPresent()) {
            attributes.add(DENY_ALL_ATTRIBUTE);
        }

        if (annotations.get(PreAuthorize.class).isPresent() || annotations.get(PostAuthorize.class).isPresent()) {
            return null;
        }
        return new AuthorizationDecision(!Collections.disjoint(attributes, authentication.get().getAuthorities()));
    }
}