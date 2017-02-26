package com.baeldung.dependency.injection.field.simple;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.Assert.assertNotNull;

public class SimpleFieldInjectionBeanTest {
    @Test
    public void field() throws Exception {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
                SimpleFieldInjectionBean.class,
                BeanA.class
        );
        SimpleFieldInjectionBean injectionBean = applicationContext.getBean(SimpleFieldInjectionBean.class);

        BeanA injectedBean = injectionBean.getBeanA();

        assertNotNull(injectedBean);
    }
}