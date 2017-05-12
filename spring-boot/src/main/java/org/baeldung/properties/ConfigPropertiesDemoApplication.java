package org.baeldung.properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

import com.baeldung.autoconfiguration.MySQLAutoconfiguration;

@EnableAutoConfiguration(exclude = MySQLAutoconfiguration.class)
@ComponentScan(basePackageClasses = ConfigProperties.class)
public class ConfigPropertiesDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConfigPropertiesDemoApplication.class);
    }
}
