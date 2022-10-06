package com.baeldung.componentscan.springapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.support.AbstractApplicationContext;

import com.baeldung.componentscan.ExampleBean;
import com.baeldung.componentscan.springapp.flowers.Rose;

@Configuration
// @ComponentScan
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = Rose.class))
// @ComponentScan(basePackages = "com.baeldung.componentscan.springapp")
// @ComponentScan(basePackages = "com.baeldung.componentscan.springapp.animals")
// @ComponentScan(basePackages =
// {"com.baeldung.componentscan.springapp.animals","com.baeldung.componentscan.springapp.flowers"})
// @ComponentScan(basePackages =
// "com.baeldung.componentscan.springapp.animals;com.baeldung.componentscan.springapp.flowers")
// @ComponentScan(basePackages =
// "com.baeldung.componentscan.springapp.animals,com.baeldung.componentscan.springapp.flowers")
// @ComponentScan(basePackages = "com.baeldung.componentscan.springapp.animals
// com.baeldung.componentscan.springapp.flowers")
// @ComponentScan(excludeFilters = @ComponentScan.Filter(type =
// FilterType.REGEX, pattern =
// "com\\.baeldung\\.componentscan\\.springapp\\.flowers\\..*"))
public class SpringComponentScanApp {

    private static final Logger log = LoggerFactory.getLogger(SpringComponentScanApp.class);

    @Bean
    public ExampleBean exampleBean() {
        return new ExampleBean();
    }

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringComponentScanApp.class);
        for (String beanName : applicationContext.getBeanDefinitionNames()) {
            log.info(beanName);
        }
        ((AbstractApplicationContext) applicationContext).close();
    }

}