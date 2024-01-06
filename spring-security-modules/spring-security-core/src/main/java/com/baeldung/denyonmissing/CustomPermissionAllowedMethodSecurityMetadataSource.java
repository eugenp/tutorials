package com.baeldung.denyonmissing;

import static org.springframework.security.access.annotation.Jsr250SecurityConfig.DENY_ALL_ATTRIBUTE;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.method.AbstractFallbackMethodSecurityMetadataSource;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

public class CustomPermissionAllowedMethodSecurityMetadataSource extends AbstractFallbackMethodSecurityMetadataSource {
    @Override
    protected Collection<ConfigAttribute> findAttributes(Class<?> clazz) {
        return null;
    }

    @Override
    protected Collection<ConfigAttribute> findAttributes(Method method, Class<?> targetClass) {
        MergedAnnotations annotations = MergedAnnotations.from(method,
                MergedAnnotations.SearchStrategy.DIRECT);
        List<ConfigAttribute> attributes = new ArrayList<>();

        MergedAnnotations classAnnotations = MergedAnnotations.from(targetClass,  MergedAnnotations.SearchStrategy.DIRECT);
        // if the class is annotated as @Controller we should by default deny access to every method
        if (classAnnotations.get(Controller.class).isPresent()) {
            attributes.add(DENY_ALL_ATTRIBUTE);
        }

        if (annotations.get(PreAuthorize.class).isPresent() || annotations.get(PostAuthorize.class).isPresent()) {
            return null;
        }

        return attributes;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }
}