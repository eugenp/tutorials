package com.baeldung.annotations;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
/**
 * Custom condition evaluated during application startup to determine whether
 * a bean definition should be included in the application context.
 *
 * Conditional checks are performed early in the bootstrap phase, and their
 * outcome affects bean registration rather than runtime behavior.
 */
public class HibernateCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        // application specific condition check
        return true;
    }

}
