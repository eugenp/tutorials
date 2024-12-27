package com.baeldung.elasticsearch.importcsv;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobRunnerConfig {

    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private Job importJob;

    @Bean
    public CommandLineRunner runJob() {
        return args -> {
            try {
                JobExecution execution = jobLauncher.run(importJob, new JobParameters());
                System.out.println("Job Status: " + execution.getStatus());
            } catch (Exception e) {
                System.err.println("Job failed with error: " + e.getMessage()); // handle exception
            }
        };
    }
}