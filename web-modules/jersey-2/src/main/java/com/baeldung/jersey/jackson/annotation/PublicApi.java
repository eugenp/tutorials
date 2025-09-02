package com.baeldung.jersey.jackson.annotation;

import java.lang.annotation.*;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface PublicApi {}