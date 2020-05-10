package com.baeldung.auth0;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.baeldung.auth0.filter.AuthFilter;

@SpringBootApplication
@ComponentScan(basePackages = "com.baeldung.auth0")
@EnableAutoConfiguration
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    @Bean
    public FilterRegistrationBean<AuthFilter> filterRegistration() {
        final FilterRegistrationBean<AuthFilter> registration = new FilterRegistrationBean<AuthFilter>();
        registration.setFilter(new AuthFilter());
        registration.addUrlPatterns("/");
        registration.addUrlPatterns("/users");
        registration.addUrlPatterns("/userByEmail");
        registration.addUrlPatterns("/createUser");
        registration.setName(AuthFilter.class.getName());
        return registration;
    }

}
