package com.baeldung.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.baeldung.controller.WelcomeController;

@Configuration
public class WebAppConfiguration_BeanNameUrlHandlerMapping {

    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    @Bean
    BeanNameUrlHandlerMapping beanNameUrlHandlerMapping() {
        BeanNameUrlHandlerMapping bean = new BeanNameUrlHandlerMapping();
        return bean;
    }

    @Bean("/beanNameUrl")
    public WelcomeController welcome() {
        WelcomeController welcome = new WelcomeController();
        return welcome;
    }

}
