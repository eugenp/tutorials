package com.baeldung;

import com.baeldung.jobrunr.service.SampleJobService;
import org.jobrunr.jobs.mappers.JobMapper;
import org.jobrunr.scheduling.JobScheduler;
import org.jobrunr.scheduling.cron.Cron;
import org.jobrunr.storage.InMemoryStorageProvider;
import org.jobrunr.storage.StorageProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class JobRunrSpringBootApp {

    @Autowired
    private JobScheduler jobScheduler;

    public static void main(String[] args) {
        SpringApplication.run(JobRunrSpringBootApp.class, args);
    }

    @Bean
    public StorageProvider storageProvider(JobMapper jobMapper) {
        InMemoryStorageProvider storageProvider = new InMemoryStorageProvider();
        storageProvider.setJobMapper(jobMapper);
        return storageProvider;
    }

    @PostConstruct
    public void scheduleRecurrently() {
        jobScheduler.<SampleJobService>scheduleRecurrently(Cron.every5minutes(), x -> x.executeSampleJob("a recurring job"));
    }
}
