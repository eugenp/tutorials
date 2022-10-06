package com.baeldung.componentscan.filter.custom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.support.AbstractApplicationContext;

@Configuration
@ComponentScan(includeFilters = @ComponentScan.Filter(type = FilterType.CUSTOM, classes = ComponentScanCustomFilter.class))
public class ComponentScanCustomFilterApp {

    private static final Logger log = LoggerFactory.getLogger(ComponentScanCustomFilterApp.class);

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(
                ComponentScanCustomFilterApp.class);
        for (String beanName : applicationContext.getBeanDefinitionNames()) {
            log.info("BN-custom: " + beanName);
        }
        ((AbstractApplicationContext) applicationContext).close();
    }
}
