package com.cars.app.web.rest;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContext;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithUnauthenticatedMockUser.Factory.class)
public @interface WithUnauthenticatedMockUser {
    class Factory implements WithSecurityContextFactory<WithUnauthenticatedMockUser> {

        @Override
        public SecurityContext createSecurityContext(WithUnauthenticatedMockUser annotation) {
            return SecurityContextHolder.createEmptyContext();
        }
    }
}
