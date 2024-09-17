package com.baeldung.quarkus.utils;

import io.quarkus.test.junit.QuarkusTest;

import jakarta.enterprise.inject.Stereotype;
import jakarta.transaction.Transactional;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@QuarkusTest
@Stereotype
@Transactional
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface QuarkusTransactionalTest {
}
