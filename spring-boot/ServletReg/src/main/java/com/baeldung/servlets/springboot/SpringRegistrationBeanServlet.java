package com.baeldung.servlets.springboot;

import com.baeldung.servlets.GenericCustomServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

<<<<<<< HEAD
@Configuration public class SpringRegistrationBeanServlet {

    @Bean public ServletRegistrationBean genericCustomServlet() {
=======
@Configuration
public class SpringRegistrationBeanServlet {

    @Bean
    public ServletRegistrationBean genericCustomServlet() {
>>>>>>> origin
        ServletRegistrationBean bean = new ServletRegistrationBean(new GenericCustomServlet(), "/springregistrationbeanservlet/*");
        bean.setLoadOnStartup(1);
        return bean;
    }
}


