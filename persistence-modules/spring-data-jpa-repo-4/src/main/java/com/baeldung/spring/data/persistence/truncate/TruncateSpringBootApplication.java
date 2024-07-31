package com.baeldung.spring.data.persistence.truncate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan(basePackageClasses = MyEntity.class)
@SpringBootApplication
public class TruncateSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(TruncateSpringBootApplication.class);
    }
}
