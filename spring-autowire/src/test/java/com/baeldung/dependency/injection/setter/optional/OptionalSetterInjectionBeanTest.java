package com.baeldung.dependency.injection.setter.optional;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.Assert.assertNull;

public class OptionalSetterInjectionBeanTest {
    @Test
    public void optionalSetter() throws Exception {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
                OptionalSetterInjectionBean.class
        );
        OptionalSetterInjectionBean optionalSetterInjectionBean = applicationContext.getBean(OptionalSetterInjectionBean.class);

        assertNull(optionalSetterInjectionBean.getDependency());
    }
}