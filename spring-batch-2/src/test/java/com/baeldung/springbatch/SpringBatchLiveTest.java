package com.baeldung.springbatch.jobs;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SpringBatchLiveTest {

    @Autowired
    private Job jobOne;

    @Autowired
    private Job jobTwo;

    @Autowired
    private SequentialJobsConfig sequentialJobsConfig;

    @Autowired
    private DynamicJobService dynamicJobService;

    @Test
    void givenJobDefinitions_whenJobsLoaded_thenJobNamesShouldMatch() {
        assertNotNull(jobOne, "jobOne should be defined");
        assertEquals("jobOne", jobOne.getName());

        assertNotNull(jobTwo, "jobTwo should be defined");
        assertEquals("jobTwo", jobTwo.getName());
    }

    @Test
    void givenSequentialJobs_whenExecuted_thenJobsRunInOrder() {
        assertDoesNotThrow(() -> sequentialJobsConfig.runJobsSequentially(), "Sequential job execution should execute");
    }

    @Test
    void givenJobData_whenJobsCreated_thenJobsRunSuccessfully() throws Exception {
        Map<String, List<String>> jobsData = new HashMap<>();
        jobsData.put("chunkJob1", Arrays.asList("data1", "data2", "data3"));
        jobsData.put("chunkJob2", Arrays.asList("data4", "data5", "data6"));

        assertDoesNotThrow(() -> dynamicJobService.createAndRunJob(jobsData), "Dynamic job creation and execution should run successfully");
    }
}
