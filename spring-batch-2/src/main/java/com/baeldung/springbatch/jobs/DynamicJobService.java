package com.baeldung.springbatch.jobs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;

@Service
public class DynamicJobService {

    private final JobRepository jobRepository;
    private final JobLauncher jobLauncher;
    private final PlatformTransactionManager transactionManager;

    public DynamicJobService(JobRepository jobRepository, JobLauncher jobLauncher, PlatformTransactionManager transactionManager) {
        this.jobRepository = jobRepository;
        this.jobLauncher = jobLauncher;
        this.transactionManager = transactionManager;
    }

    public void createAndRunJob(Map<String, List<String>> jobsData) throws Exception {
        List<Job> jobs = new ArrayList<>();

        // Create chunk-oriented jobs
        for (Map.Entry<String, List<String>> entry : jobsData.entrySet()) {
            if (entry.getValue() instanceof List) {
                jobs.add(createJob(entry.getKey(), entry.getValue()));
            }
        }

        // Run all jobs
        for (Job job : jobs) {
            JobParameters jobParameters = new JobParametersBuilder().addString("jobID", String.valueOf(System.currentTimeMillis()))
                .toJobParameters();
            jobLauncher.run(job, jobParameters);
        }
    }

    private Job createJob(String jobName, List<String> data) {
        return new JobBuilder(jobName, jobRepository).start(createStep(data))
            .build();
    }

    private Step createStep(List<String> data) {
        return new StepBuilder("step", jobRepository).<String, String> chunk(10, transactionManager)
            .reader(new ListItemReader<>(data))
            .processor(item -> item.toUpperCase())
            .writer(items -> items.forEach(System.out::println))
            .build();
    }
}
