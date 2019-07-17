package org.baeldung.session.exception;

import org.baeldung.demo.model.Foo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan(basePackageClasses = Foo.class)
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        System.setProperty("spring.config.name", "exception");
        System.setProperty("spring.profiles.active", "exception");
        SpringApplication.run(Application.class, args);
    }
}
