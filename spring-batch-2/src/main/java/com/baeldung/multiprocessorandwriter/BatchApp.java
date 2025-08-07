package com.baeldung.multiprocessorandwriter;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BatchApp {

    public static void main(String[] args) {
        SpringApplication.run(BatchApp.class, args);
    }

    @Bean
    CommandLineRunner run(JobLauncher jobLauncher, Job job) {
        return args -> {
            JobParameters parameters = new JobParametersBuilder().addLong("startAt", System.currentTimeMillis())
                .toJobParameters();
            jobLauncher.run(job, parameters);
        };
    }
}