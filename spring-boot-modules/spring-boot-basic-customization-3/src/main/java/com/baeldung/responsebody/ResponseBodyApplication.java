package com.baeldung.responsebody;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.baeldung.responsebody")
public class ResponseBodyApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResponseBodyApplication.class, args);
    }
}
