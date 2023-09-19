package com.baeldung.selfinvocation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.EnableLoadTimeWeaving;

@SpringBootApplication
@EnableCaching(mode = AdviceMode.ASPECTJ)
@EnableLoadTimeWeaving
public class LoadTimeWeavingApplication {
    public static void main(String[] args) {
        SpringApplication.run(LoadTimeWeavingApplication.class, args);
    }
}