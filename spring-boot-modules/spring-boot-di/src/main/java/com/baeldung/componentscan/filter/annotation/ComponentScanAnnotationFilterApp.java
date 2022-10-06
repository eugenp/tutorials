package com.baeldung.componentscan.filter.annotation;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.support.AbstractApplicationContext;

@Configuration
@ComponentScan(includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Animal.class))
public class ComponentScanAnnotationFilterApp {

    private static final Logger log = LoggerFactory.getLogger(ComponentScanAnnotationFilterApp.class);

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(
                ComponentScanAnnotationFilterApp.class);
        List<String> beans = Arrays.stream(applicationContext.getBeanDefinitionNames())
                .filter(bean -> !bean.contains("org.springframework")
                        && !bean.contains("componentScanAnnotationFilterApp"))
                .collect(Collectors.toList());
        log.info(beans.toString());
        ((AbstractApplicationContext) applicationContext).close();
    }
    
}
