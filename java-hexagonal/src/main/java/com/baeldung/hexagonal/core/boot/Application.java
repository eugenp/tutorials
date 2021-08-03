package com.baeldung.hexagonal.core.boot;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Application {
    public String mode() default "";
}