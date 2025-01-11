package com.baeldung.springbatch.jobs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Configuration
@EnableScheduling
public class ScheduledJobs {

    private static final Logger log = LoggerFactory.getLogger(SequentialJobsConfig.class);

    @Autowired
    private Job jobOne;

    @Autowired
    private Job jobTwo;

    @Autowired
    private JobLauncher jobLauncher;

    @Scheduled(cron = "0 */1 * * * *")  // Run every minutes
    public void runJob1() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder().addString("ID", "Scheduled 1")
            .toJobParameters();

        jobLauncher.run(jobOne, jobParameters);
    }

    @Scheduled(fixedRate = 1000 * 60 * 3)  // Run every 3 minutes
    public void runJob2() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder().addString("ID", "Scheduled 2")
            .toJobParameters();

        jobLauncher.run(jobTwo, jobParameters);
    }

}
