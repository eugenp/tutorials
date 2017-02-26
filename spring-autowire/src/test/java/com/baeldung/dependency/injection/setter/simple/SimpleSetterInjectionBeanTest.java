package com.baeldung.dependency.injection.setter.simple;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.Assert.assertNotNull;

public class SimpleSetterInjectionBeanTest {
    @Test
    public void setter() throws Exception {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
                SimpleSetterInjectionBean.class,
                BeanA.class
        );
        SimpleSetterInjectionBean injectionBean = applicationContext.getBean(SimpleSetterInjectionBean.class);

        BeanA injectedBean = injectionBean.getBeanA();

        assertNotNull(injectedBean);
    }
}