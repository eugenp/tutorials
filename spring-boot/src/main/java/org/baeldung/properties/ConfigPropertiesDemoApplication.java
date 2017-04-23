package org.baeldung.properties;

import com.baeldung.autoconfiguration.MySQLAutoconfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@EnableAutoConfiguration(exclude = MySQLAutoconfiguration.class)
@ComponentScan(basePackageClasses = ConfigProperties.class)
public class ConfigPropertiesDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConfigPropertiesDemoApplication.class);
    }
}
