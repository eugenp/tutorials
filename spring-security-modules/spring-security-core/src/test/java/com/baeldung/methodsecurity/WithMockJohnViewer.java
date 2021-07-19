package com.baeldung.methodsecurity;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.security.test.context.support.WithMockUser;

@Retention(RetentionPolicy.RUNTIME)
@WithMockUser(value="john",roles="VIEWER")
public @interface WithMockJohnViewer { }