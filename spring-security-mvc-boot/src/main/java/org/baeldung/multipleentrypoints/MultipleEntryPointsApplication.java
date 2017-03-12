package org.baeldung.multipleentrypoints;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// @ImportResource({"classpath*:spring-security-multiple-entry.xml"})
public class MultipleEntryPointsApplication {
    public static void main(String[] args) {
        SpringApplication.run(MultipleEntryPointsApplication.class, args);
    }
}
