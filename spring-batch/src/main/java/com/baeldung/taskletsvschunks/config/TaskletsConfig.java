package com.baeldung.taskletsvschunks.config;

import com.baeldung.taskletsvschunks.tasklets.LinesProcessor;
import com.baeldung.taskletsvschunks.tasklets.LinesReader;
import com.baeldung.taskletsvschunks.tasklets.LinesWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class TaskletsConfig {

    @Bean
    public LinesReader linesReader() {
        return new LinesReader();
    }

    @Bean
    public LinesProcessor linesProcessor() {
        return new LinesProcessor();
    }

    @Bean
    public LinesWriter linesWriter() {
        return new LinesWriter();
    }

    @Bean
    protected Step readLines(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("readLines", jobRepository)
          .tasklet(linesReader(), transactionManager)
          .build();
    }

    @Bean
    protected Step processLines(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("processLines", jobRepository)
          .tasklet(linesProcessor(), transactionManager)
          .build();
    }

    @Bean
    protected Step writeLines(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("writeLines", jobRepository)
          .tasklet(linesWriter(), transactionManager)
          .build();
    }

    @Bean
    public Job job(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobBuilder("taskletsJob", jobRepository)
          .start(readLines(jobRepository, transactionManager))
          .next(processLines(jobRepository, transactionManager))
          .next(writeLines(jobRepository, transactionManager))
          .build();
    }

}
