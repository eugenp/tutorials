package com.baeldung.jobrunr;

import com.baeldung.jobrunr.service.SampleJobService;
import jakarta.annotation.PostConstruct;
import org.jobrunr.scheduling.JobScheduler;
import org.jobrunr.scheduling.cron.Cron;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class JobRunrSpringBootApp {

    @Autowired
    private JobScheduler jobScheduler;

    public static void main(String[] args) {
        SpringApplication.run(JobRunrSpringBootApp.class, args);
    }

    @PostConstruct
    public void scheduleRecurrently() {
        jobScheduler.<SampleJobService>scheduleRecurrently(Cron.every5minutes(), x -> x.executeSampleJob("a recurring job"));
    }
}
