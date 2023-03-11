package com.baeldung.taskletsvschunks.config;

import com.baeldung.taskletsvschunks.tasklets.LinesProcessor;
import com.baeldung.taskletsvschunks.tasklets.LinesReader;
import com.baeldung.taskletsvschunks.tasklets.LinesWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.TaskExecutorJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class TaskletsConfig {

    @Bean
    public JobLauncherTestUtils jobLauncherTestUtils() {
        return new JobLauncherTestUtils();
    }

    /*@Bean
    public JobRepository jobRepository() throws Exception {
        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
        factory.setDataSource(dataSource());
        factory.setTransactionManager(transactionManager());
        return factory.getObject();
    }*/

    /*@Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.sqlite.JDBC");
        dataSource.setUrl("jdbc:sqlite:repository.sqlite");
        return dataSource;
    }*/

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new ResourcelessTransactionManager();
    }

    /*@Bean
    public JobLauncher jobLauncher() throws Exception {
        TaskExecutorJobLauncher jobLauncher = new TaskExecutorJobLauncher();
        jobLauncher.setJobRepository(jobRepository());
        return jobLauncher;
    }*/

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
