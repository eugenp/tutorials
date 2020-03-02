package com.baeldung.servlets.servlets.springboot;

import com.baeldung.servlets.servlets.GenericCustomServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringRegistrationBeanServlet {

    @Bean
    public ServletRegistrationBean<GenericCustomServlet> genericCustomServlet() {
        ServletRegistrationBean<GenericCustomServlet> bean = new ServletRegistrationBean<>(new GenericCustomServlet(), "/springregistrationbeanservlet/*");
        bean.setLoadOnStartup(1);
        return bean;
    }
}
