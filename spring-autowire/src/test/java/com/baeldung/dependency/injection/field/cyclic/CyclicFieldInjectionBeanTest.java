package com.baeldung.dependency.injection.field.cyclic;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

public class CyclicFieldInjectionBeanTest {
    @Test
    public void cyclicField() throws Exception {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
                CyclicFieldInjectionBean.class,
                BeanA.class
        );
        CyclicFieldInjectionBean injectionBean = applicationContext.getBean(CyclicFieldInjectionBean.class);

        BeanA beanA = injectionBean.getBeanA();

        assertNotNull(beanA);
        assertSame(injectionBean, beanA.getParent());
    }
}