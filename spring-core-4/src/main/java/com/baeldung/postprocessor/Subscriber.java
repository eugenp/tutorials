package com.baeldung.postprocessor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation which indicates which Guava {@link com.google.common.eventbus.EventBus} a Spring bean wishes to subscribe to.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface Subscriber {

    /**
     * A SpEL expression which selects the {@link com.google.common.eventbus.EventBus}.
     */
    String value() default GlobalEventBus.GLOBAL_EVENT_BUS_EXPRESSION;
}
