package org.baeldung.main;

import org.baeldung.boot.controller.servlet.HelloWorldServlet;
import org.baeldung.boot.controller.servlet.SpringHelloWorldServlet;
import org.baeldung.common.error.SpringHelloServletRegistrationBean;
import org.baeldung.common.resources.ExecutorServiceExitCodeGenerator;
import org.baeldung.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.autoconfiguration.MySQLAutoconfiguration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@EnableAutoConfiguration(exclude = MySQLAutoconfiguration.class)
@ComponentScan({ "org.baeldung.common.error", "org.baeldung.common.error.controller", "org.baeldung.common.properties", "org.baeldung.common.resources", "org.baeldung.endpoints", "org.baeldung.service", "org.baeldung.monitor.jmx", "org.baeldung.boot.config" })
public class SpringBootApplication {

    private static ApplicationContext applicationContext;

    @Autowired
    private LoginService service;

    @RequestMapping("/")
    String home() {
        service.login("admin", "admin".toCharArray());
        return "TADA!!! You are in Spring Boot Actuator test application.";
    }

    public static void main(String[] args) {
        applicationContext = SpringApplication.run(SpringBootApplication.class, args);
    }

    @Bean
    public ExecutorService executorService() {
        return Executors.newFixedThreadPool(10);
    }

    @Bean
    public HelloWorldServlet helloWorldServlet() {
        return new HelloWorldServlet();
    }

    @Bean
    public SpringHelloServletRegistrationBean servletRegistrationBean() {
        SpringHelloServletRegistrationBean bean = new SpringHelloServletRegistrationBean(new SpringHelloWorldServlet(), "/springHelloWorld/*");
        bean.setLoadOnStartup(1);
        bean.addInitParameter("message", "SpringHelloWorldServlet special message");
        return bean;
    }

    @Bean
    @Autowired
    public ExecutorServiceExitCodeGenerator executorServiceExitCodeGenerator(ExecutorService executorService) {
        return new ExecutorServiceExitCodeGenerator(executorService);
    }

    public void shutDown(ExecutorServiceExitCodeGenerator executorServiceExitCodeGenerator) {
        SpringApplication.exit(applicationContext, executorServiceExitCodeGenerator);
    }

}
