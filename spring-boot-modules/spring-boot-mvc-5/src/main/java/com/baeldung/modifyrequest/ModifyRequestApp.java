package com.baeldung.modifyrequest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.baeldung.modifyrequest")
public class ModifyRequestApp {
    public static void main(String[] args) {
        SpringApplication.run(ModifyRequestApp.class, args);
    }
}
