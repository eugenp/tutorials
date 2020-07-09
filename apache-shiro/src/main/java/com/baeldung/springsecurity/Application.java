package com.baeldung.springsecurity;

import org.apache.shiro.spring.boot.autoconfigure.ShiroAnnotationProcessorAutoConfiguration;
import org.apache.shiro.spring.boot.autoconfigure.ShiroAutoConfiguration;
import org.apache.shiro.spring.config.web.autoconfigure.ShiroWebAutoConfiguration;
import org.apache.shiro.spring.config.web.autoconfigure.ShiroWebFilterConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {ShiroAutoConfiguration.class,
        ShiroAnnotationProcessorAutoConfiguration.class,
        ShiroWebAutoConfiguration.class,
        ShiroWebFilterConfiguration.class})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}