package com.baeldung.dependency.injection.constructor.simple;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.Assert.assertNotNull;

public class SimpleConstructorInjectionBeanTest {
    @Test
    public void constructor() throws Exception {
        ApplicationContext applicationContext = givenSpringContainerGetInitializedWith(
                SimpleConstructorInjectionBean.class,
                BeanA.class);
        SimpleConstructorInjectionBean constructorInjectionBean = applicationContext.getBean(SimpleConstructorInjectionBean.class);
        BeanA injectedBean = constructorInjectionBean.getBeanA();

        // then
        assertNotNull(injectedBean);
    }

    private ApplicationContext givenSpringContainerGetInitializedWith(Class... classes) {
        return new AnnotationConfigApplicationContext(
                classes
        );
    }
}