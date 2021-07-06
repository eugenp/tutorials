package com.baeldung.monitoring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LagAnalyzerApplication {

    public static void main(String[] args) {
        SpringApplication.run(LagAnalyzerApplication.class, args);
        while (true) ;
    }
}
