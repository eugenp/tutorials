package com.baeldung.dapr.workflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.dapr.spring.workflows.config.EnableDaprWorkflows;

@SpringBootApplication
@EnableDaprWorkflows
public class DaprWorkflowApp {

    public static void main(String[] args) {
        SpringApplication.run(DaprWorkflowApp.class, args);
    }
}
