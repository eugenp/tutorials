package com.baeldung.customidgenerator;

import org.hibernate.annotations.IdGeneratorType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;


@IdGeneratorType(MovieIdGenerator.class)
@Target({FIELD})
@Retention(RetentionPolicy.RUNTIME)
@interface MovieGeneratedId {
}
