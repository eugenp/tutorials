package com.baeldung.a2a.server.salaryevaluator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:application-salary-evaluator-server.properties")
public class SalaryEvaluatorServer {

    public static void main(String[] args) {
        SpringApplication.run(SalaryEvaluatorServer.class, args);
    }
}