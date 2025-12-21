package com.baeldung.annotations;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * Spring {@link Condition} that is evaluated during application startup
 * to determine whether a bean definition should be included in the
 * application context.
 *
 * <p>
 * Conditions are checked early in the bootstrap phase, and the result
 * affects bean registration rather than runtime behavior.
 * </p>
 */
public class HibernateCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        // Application-specific condition check
        return true;
    }

}
