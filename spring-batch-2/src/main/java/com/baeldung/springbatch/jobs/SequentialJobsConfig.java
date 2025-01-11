package com.baeldung.springbatch.jobs;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SequentialJobsConfig {

    @Autowired
    private Job jobOne;

    @Autowired
    private Job jobTwo;

    @Autowired
    private JobLauncher jobLauncher;

    public void runJobsSequentially() {
        JobParameters jobParameters = new JobParametersBuilder().addString("ID", "Sequential 1")
            .toJobParameters();

        JobParameters jobParameters2 = new JobParametersBuilder().addString("ID", "Sequential 2")
            .toJobParameters();

        // Run jobs one after another
        try {
            jobLauncher.run(jobOne, jobParameters);
            jobLauncher.run(jobTwo, jobParameters2);
        } catch (Exception e) {
            // handle exception
            e.printStackTrace();
        }
    }

}
