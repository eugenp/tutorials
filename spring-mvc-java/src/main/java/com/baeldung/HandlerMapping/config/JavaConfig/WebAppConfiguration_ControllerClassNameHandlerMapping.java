package com.baeldung.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.mvc.support.ControllerClassNameHandlerMapping;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.baeldung.controller.WelcomeController;

@Configuration
public class WebAppConfiguration_ControllerClassNameHandlerMapping {

    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    @Bean
    public ControllerClassNameHandlerMapping controllerClassNameHandlerMapping() {
        ControllerClassNameHandlerMapping controllerClassNameHandlerMapping = new ControllerClassNameHandlerMapping();
        return controllerClassNameHandlerMapping;
    }

    @Bean
    public WelcomeController welcome() {
        WelcomeController welcome = new WelcomeController();
        return welcome;
    }

}
