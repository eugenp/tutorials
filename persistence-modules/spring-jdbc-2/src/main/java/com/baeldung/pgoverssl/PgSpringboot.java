package com.baeldung.pgoverssl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.baeldung")
public class PgSpringboot {

    public static void main(String[] args) {
        SpringApplication.run(PgSpringboot.class, args);
    }
}
