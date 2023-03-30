package com.baeldung.aliasfor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@RequestMapping
public @interface MyMapping {

    @AliasFor(annotation = RequestMapping.class, attribute = "method")
    RequestMethod[] action() default {};
    
    @AliasFor(annotation = RequestMapping.class, attribute = "path")
    String[] value() default {};

    @AliasFor(annotation = RequestMapping.class, attribute = "path")
    String[] mapping() default {};
    
    @AliasFor(annotation = RequestMapping.class, attribute = "path")
    String[] route() default {};

}
