package com.baeldung.injectiontypes;

import com.baeldung.bean.ConstructorInjectionDemoBean;
import com.baeldung.bean.FieldInjectionDemoBean;
import com.baeldung.bean.SetterInjectionDemoBean;
import com.baeldung.configuration.ApplicationContextTestBeanInjectionType;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DependencyInjectionTest {

    @Test
    public void givenAutowired_WhenSetOnConstructor_ThenDependencyResolved() {
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationContextTestBeanInjectionType.class);
        ConstructorInjectionDemoBean demoBean = (ConstructorInjectionDemoBean) context.getBean("constructorInjectionDemoBean");

        assertNotNull(demoBean.getDemoDependency());
        assertEquals("Test", demoBean.getDemoDependency().getMessage());
    }

    @Test
    public void givenAutowired_WhenSetOnSetter_ThenDependencyResolved() {
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationContextTestBeanInjectionType.class);
        SetterInjectionDemoBean demoBean = (SetterInjectionDemoBean) context.getBean("setterInjectionDemoBean");

        assertNotNull(demoBean.getDemoDependency());
        assertEquals("Test", demoBean.getDemoDependency().getMessage());
    }

    @Test
    public void givenAutowired_WhenSetOnField_ThenDependencyResolved() {
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationContextTestBeanInjectionType.class);
        FieldInjectionDemoBean demoBean = (FieldInjectionDemoBean) context.getBean("fieldInjectionDemoBean");

        assertNotNull(demoBean.getDemoDependency());
        assertEquals("Test", demoBean.getDemoDependency().getMessage());
    }
}
