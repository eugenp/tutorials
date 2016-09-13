package org.baeldung.spring.cloud;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.task.configuration.EnableTask;

@EnableTask
@EnableBatchProcessing
@SpringBootApplication
public class BatchJobApplication {

    public static void main(String[] args) {
        SpringApplication.run(BatchJobApplication.class, args);
    }
}
