package com.baeldung.restartjob;

import java.util.List;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RestartJobBatchApp {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(RestartJobBatchApp.class);
        app.setAdditionalProfiles("restart");
        app.run(args);
    }

    @Bean
    @ConditionalOnProperty(prefix = "job.autorun", name = "enabled", havingValue = "true", matchIfMissing = true)
    CommandLineRunner run(JobLauncher jobLauncher, Job job, JobExplorer jobExplorer,
        JobOperator jobOperator, BatchConfig.RestartItemProcessor itemProcessor) {
        return args -> {
          JobParameters jobParameters = new JobParametersBuilder()
              .addString("jobId", "test-job-" + System.currentTimeMillis())
              .toJobParameters();

          List<JobInstance> instances = jobExplorer.getJobInstances("simpleJob", 0, 1);
          if (!instances.isEmpty()) {
              JobInstance lastInstance = instances.get(0);
              List<JobExecution> executions = jobExplorer.getJobExecutions(lastInstance);
              if (!executions.isEmpty()) {
                  JobExecution lastExecution = executions.get(0);
                  if (lastExecution.getStatus() == BatchStatus.FAILED) {
                      System.out.println("Restarting failed job execution with ID: " + lastExecution.getId());
                      itemProcessor.setFailOnItem3(false);

                      JobExecution restartedExecution = jobLauncher.run(job, jobParameters);

                      // final Long restartId = jobOperator.restart(lastExecution.getId());
                      // final JobExecution restartedExecution = jobExplorer.getJobExecution(restartedExecution);

                      System.out.println("Restarted job status: " + restartedExecution.getStatus());
                      return;
                  }
              }
          }

          System.out.println("Starting new job execution...");
          JobExecution jobExecution = jobLauncher.run(job, jobParameters);
          System.out.println("Job started with status: " + jobExecution.getStatus());
      };
    }
}