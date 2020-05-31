package com.baeldung.loginextrafieldssimple;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.baeldung.loginextrafieldssimple")
@EnableJpaRepositories(basePackages = "com.baeldung.dao")
@EntityScan(basePackages = "com.baeldung.domain")
public class ExtraLoginFieldsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExtraLoginFieldsApplication.class, args);
    }

}
