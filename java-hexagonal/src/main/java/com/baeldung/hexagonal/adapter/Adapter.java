package com.baeldung.hexagonal.adapter;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Adapter {
    public String defaultPort() default "";
}