package com.baeldung.componentscan.filter.annotation;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

public class ComponentScanAnnotationFilterAppTest {

    @Autowired
    private ApplicationContext context;

    @Before
    public void getContext() {
        context = new AnnotationConfigApplicationContext(
                ComponentScanAnnotationFilterApp.class);
    }

    @Test
    public void whenAnnotationFilterIsUsed_thenComponentScanShouldRegisterBeanAnnotatedWithAnimalAnootation() {
        List<String> beans = Arrays.stream(context.getBeanDefinitionNames())
                .filter(bean -> !bean.contains("org.springframework")
                        && !bean.contains("componentScanAnnotationFilterApp"))
                .collect(Collectors.toList());
        assertEquals(1, beans.size());
        assertEquals("elephant", beans.get(0));
    }

    @After
    public void closeContext() {
        ((AbstractApplicationContext) context).close();
    }

}
