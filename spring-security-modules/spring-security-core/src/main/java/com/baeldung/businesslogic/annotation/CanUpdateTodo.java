package com.baeldung.businesslogic.annotation;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("@securityService.canUpdateTodo(#todo, principal)")
public @interface CanUpdateTodo {
}
