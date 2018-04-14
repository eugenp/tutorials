package com.baeldung.errorhandling;

import com.baeldung.autoconfiguration.MySQLAutoconfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@SpringBootApplication(exclude = {MySQLAutoconfiguration.class})
@ComponentScan(basePackages = "com.baeldung.errorhandling")
public class ErrorHandlingApplication {

    public static void main(String [] args) {
        System.setProperty("spring.profiles.active", "errorhandling");
        SpringApplication.run(ErrorHandlingApplication.class, args);
    }

    @Bean(name = "mvcHandlerMappingIntrospector")
    public HandlerMappingIntrospector mvcHandlerMappingIntrospector(ApplicationContext context) {
        return new HandlerMappingIntrospector(context);
    }


}
