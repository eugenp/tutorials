package com.baeldung.springbootmvc.jsfapplication;

import javax.faces.webapp.FacesServlet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.baeldung.springbootmvc.jsfapplication.controller.JsfController;
import com.baeldung.springbootmvc.jsfapplication.model.TodoDao;
import com.baeldung.springbootmvc.jsfapplication.service.TodoService;

@SpringBootApplication
@ComponentScan(basePackageClasses = { JsfController.class, TodoDao.class, TodoService.class })
public class JsfApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(JsfApplication.class, args);
    }

    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
        FacesServlet servlet = new FacesServlet();
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(servlet, "*.jsf");
        return servletRegistrationBean;
    }

}
