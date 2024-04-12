package com.baeldung.ex.beancreationexception.cause9;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Autowired
    BeanFactory beanFactory;

    @Bean
    public BeanB beanB() {
        beanFactory.getBean("beanA");
        return new BeanB();
    }
}
