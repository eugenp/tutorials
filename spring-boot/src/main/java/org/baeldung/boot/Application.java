package org.baeldung.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.baeldung.autoconfiguration.MySQLAutoconfiguration;

@SpringBootApplication(exclude = MySQLAutoconfiguration.class)
public class Application {
    private static ApplicationContext applicationContext;

    public static void main(String[] args) {
        applicationContext = SpringApplication.run(Application.class, args);
    }
}
