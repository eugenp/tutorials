package com.baeldung.enablemethodsecurity.authorization;

import java.util.Optional;
import java.util.function.Supplier;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;

import com.baeldung.enablemethodsecurity.services.Policy;
import com.baeldung.enablemethodsecurity.services.PolicyEnum;
import com.baeldung.enablemethodsecurity.user.SecurityUser;

public class CustomAuthorizationManager<T> implements AuthorizationManager<MethodInvocation> {
    private final AuthenticationTrustResolver trustResolver = new AuthenticationTrustResolverImpl();

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, MethodInvocation methodInvocation) {

        if (hasAuthentication(authentication.get())) {

            Policy policyAnnotation = AnnotationUtils.findAnnotation(methodInvocation.getMethod(), Policy.class);

            SecurityUser user = (SecurityUser) authentication.get()
              .getPrincipal();

            return new AuthorizationDecision(Optional.ofNullable(policyAnnotation)
              .map(Policy::value)
              .filter(policy -> policy == PolicyEnum.OPEN || (policy == PolicyEnum.RESTRICTED && user.hasAccessToRestrictedPolicy()))
              .isPresent());

        }

        return new AuthorizationDecision(false);
    }

    private boolean hasAuthentication(Authentication authentication) {
        return authentication != null && isNotAnonymous(authentication) && authentication.isAuthenticated();
    }

    private boolean isNotAnonymous(Authentication authentication) {
        return !this.trustResolver.isAnonymous(authentication);
    }
}
