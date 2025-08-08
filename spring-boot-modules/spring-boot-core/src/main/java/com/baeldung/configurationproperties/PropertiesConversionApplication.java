package com.baeldung.configurationproperties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackageClasses = { PropertyConversion.class, EmployeeConverter.class })
public class PropertiesConversionApplication {
    public static void main(String[] args) {
        SpringApplication.run(PropertiesConversionApplication.class, args);
    }

}
