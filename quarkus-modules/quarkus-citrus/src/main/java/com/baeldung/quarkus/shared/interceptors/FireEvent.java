package com.baeldung.quarkus.shared.interceptors;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.enterprise.util.Nonbinding;
import jakarta.interceptor.InterceptorBinding;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Annotate a method to get an event fired after method execution.
 */
@Inherited
@Documented
@InterceptorBinding
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface FireEvent {

    /**
     * The event class. This class needs a constructor with the same parameters as the method.
     *
     * @return the event class
     */
    @Nonbinding Class<?> value();

    @Nonbinding FireMode mode() default FireMode.SYNC_AND_ASYNC;

    @RequiredArgsConstructor
    @Getter(AccessLevel.PACKAGE)
    enum FireMode {

        ONLY_SYNC(true, false),
        ONLY_ASYNC(false, true),
        SYNC_AND_ASYNC(true, true);

        private final boolean fireSync;
        private final boolean fireAsync;

    }

}