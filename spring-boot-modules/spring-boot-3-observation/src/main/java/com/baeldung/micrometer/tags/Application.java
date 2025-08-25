package com.baeldung.micrometer.tags;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.micrometer.core.aop.MeterTagAnnotationHandler;

@SpringBootApplication
class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public MeterTagAnnotationHandler meterTagAnnotationHandler() {
        return new MeterTagAnnotationHandler(aClass -> Object::toString, aClass -> (exp, param) -> "");
    }

}
