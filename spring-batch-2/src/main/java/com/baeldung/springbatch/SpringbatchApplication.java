package com.baeldung.springbatch;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration;

import com.baeldung.springbatch.jobs.DynamicJobService;
import com.baeldung.springbatch.jobs.ParallelJobService;
import com.baeldung.springbatch.jobs.SequentialJobsConfig;

@SpringBootApplication(exclude = { BatchAutoConfiguration.class })
public class SpringbatchApplication implements CommandLineRunner {

    @Autowired
    private SequentialJobsConfig sequentialJobsConfig;

    @Autowired
    private DynamicJobService dynamicJobService;

    @Autowired
    private ParallelJobService parallelJobService;

    public static void main(String[] args) {
        SpringApplication.run(SpringbatchApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // running sequentials jobs
        sequentialJobsConfig.runJobsSequentially();

        // running dynamic jobs
        Map<String, List<String>> jobsData = new HashMap<>();
        jobsData.put("chunkJob1", Arrays.asList("data1", "data2", "data3"));
        jobsData.put("chunkJob2", Arrays.asList("data4", "data5", "data6"));
        dynamicJobService.createAndRunJob(jobsData);

        // parallel job service
        parallelJobService.runJobsInParallel();
    }

}
