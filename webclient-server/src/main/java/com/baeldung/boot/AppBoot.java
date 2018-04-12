package com.baeldung.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.baeldung.*")
public class AppBoot {
	public static void main(String[] args) {
        SpringApplication.run(AppBoot.class, args);
    }
}
