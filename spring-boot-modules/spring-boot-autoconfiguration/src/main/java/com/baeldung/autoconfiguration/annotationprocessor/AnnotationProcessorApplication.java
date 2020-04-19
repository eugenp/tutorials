package com.baeldung.autoconfiguration.annotationprocessor;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

import com.baeldung.autoconfiguration.MySQLAutoconfiguration;

@EnableAutoConfiguration(exclude = { MySQLAutoconfiguration.class})
@ComponentScan(basePackageClasses = {DatabaseProperties.class})
public class AnnotationProcessorApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(AnnotationProcessorApplication.class).run();
    }
}
