package com.baeldung.componentscan.filter.custom;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

public class ComponentScanCustomFilterAppTest {

        @Test
        public void whenCustomFilterIsUsed_thenComponentScanShouldRegisterBeanMatchingCustomFilter() {
                ApplicationContext applicationContext = new AnnotationConfigApplicationContext(
                                ComponentScanCustomFilterApp.class);
                List<String> beans = Arrays.stream(applicationContext.getBeanDefinitionNames())
                                .filter(bean -> !bean.contains("org.springframework")
                                                && !bean.contains("componentScanCustomFilterApp")
                                                && !bean.contains("componentScanCustomFilter"))
                                .collect(Collectors.toList());
                assertEquals(1, beans.size());
                assertEquals("elephant", beans.get(0));
                ((AbstractApplicationContext) applicationContext).close();
        }
}
