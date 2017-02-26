package com.baeldung.dependency.injection.setter.cyclic;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.Assert.*;

public class CyclicSetterInjectionBeanTest {
    @Test
    public void cyclicSetter() throws Exception {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
                CyclicSetterInjectionBean.class,
                DependentBean.class
        );
        CyclicSetterInjectionBean injectionBean = applicationContext.getBean(CyclicSetterInjectionBean.class);

        DependentBean dependentBean = injectionBean.getDependentBean();

        assertNotNull(dependentBean);
        assertSame(injectionBean, dependentBean.getParent());
    }
}