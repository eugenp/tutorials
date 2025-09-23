package com.baeldung.micrometer.tags;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.micrometer.core.aop.CountedAspect;
import io.micrometer.core.aop.CountedMeterTagAnnotationHandler;
import io.micrometer.core.aop.MeterTagAnnotationHandler;
import io.micrometer.core.aop.TimedAspect;

@SpringBootApplication
class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public MeterTagAnnotationHandler meterTagAnnotationHandler() {
        return new MeterTagAnnotationHandler(
            aClass -> Object::toString,
            aClass -> (exp, param) -> "");
    }

    @Bean
    public CountedAspect countedAspect() {
        CountedAspect aspect = new CountedAspect();
        CountedMeterTagAnnotationHandler tagAnnotationHandler = new CountedMeterTagAnnotationHandler(
            aClass -> Object::toString,
            aClass -> (exp, param) -> "");
        aspect.setMeterTagAnnotationHandler(tagAnnotationHandler);
        return aspect;
    }

}
