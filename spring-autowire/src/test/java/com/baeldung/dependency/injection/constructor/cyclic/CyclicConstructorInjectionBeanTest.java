package com.baeldung.dependency.injection.constructor.cyclic;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.UnsatisfiedDependencyException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class CyclicConstructorInjectionBeanTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void cyclicConstructor() throws Exception {
        expectedException.expect(UnsatisfiedDependencyException.class);

        new AnnotationConfigApplicationContext(CyclicConstructorInjectionBean.class, BeanA.class);
    }
}