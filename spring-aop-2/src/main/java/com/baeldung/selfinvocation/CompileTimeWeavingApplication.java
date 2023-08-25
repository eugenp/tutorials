package com.baeldung.selfinvocation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.AdviceMode;

@SpringBootApplication
@EnableCaching(mode = AdviceMode.ASPECTJ)
public class CompileTimeWeavingApplication {
    public static void main(String[] args) {
        SpringApplication.run(CompileTimeWeavingApplication.class, args);
    }
}