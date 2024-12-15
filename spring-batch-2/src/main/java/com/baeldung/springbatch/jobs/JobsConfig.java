package com.baeldung.springbatch.jobs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class JobsConfig {

    private static final Logger log = LoggerFactory.getLogger(SequentialJobsConfig.class);

    @Bean
    public Job jobOne(JobRepository jobRepository, Step stepOne) {
        return new JobBuilder("jobOne", jobRepository).start(stepOne)
            .build();
    }

    @Bean
    public Step stepOne(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("stepOne", jobRepository).tasklet((contribution, chunkContext) -> {
                log.info("Hello");
                return RepeatStatus.FINISHED;
            }, transactionManager)
            .build();
    }

    @Bean
    public Job jobTwo(JobRepository jobRepository, Step stepTwo) {
        return new JobBuilder("jobTwo", jobRepository).start(stepTwo)
            .build();
    }

    @Bean
    public Step stepTwo(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("stepTwo", jobRepository).tasklet((contribution, chunkContext) -> {
                log.info("World");
                return RepeatStatus.FINISHED;
            }, transactionManager)
            .build();
    }
}
