package com.baeldung.transformers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:application-transformers.yaml")
@SpringBootApplication(scanBasePackageClasses = TransformersApplication.class)
public class TransformersApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransformersApplication.class, args);
    }
}
