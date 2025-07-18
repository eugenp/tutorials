package com.baeldung.restartjob;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@SpringBootTest(classes = {RestartJobBatchApp.class, BatchConfig.class},
    properties = {"job.autorun.enabled=false"})
@Import(RestartJobBatchAppIntegrationTest.TestConfig.class)
public class RestartJobBatchAppIntegrationTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private BatchConfig.RestartItemProcessor itemProcessor;

    @TestConfiguration
    static class TestConfig {
        @Autowired
        private JobLauncher jobLauncher;

        @Autowired
        private Job job;

        @Bean
        public JobLauncherTestUtils jobLauncherTestUtils() {
            JobLauncherTestUtils jobLauncherTestUtils = new JobLauncherTestUtils();
            jobLauncherTestUtils.setJobLauncher(jobLauncher);
            jobLauncherTestUtils.setJob(job);
            return jobLauncherTestUtils;
        }
    }

    private final Resource inputFile = new ClassPathResource("data.csv");

    @Test
    public void givenItems_whenFailed_thenRestartFromFailure() throws Exception {
        // Given
        createTestFile("Item1\nItem2\nItem3\nItem4");

        JobParameters jobParameters = new JobParametersBuilder()
          .addLong("time", System.currentTimeMillis())
          .toJobParameters();

        // When
        JobExecution firstExecution = jobLauncherTestUtils.launchJob(jobParameters);
        assertEquals(BatchStatus.FAILED, firstExecution.getStatus());

        Long executionId = firstExecution.getId();

        itemProcessor.setFailOnItem3(false);

        // Then
        JobExecution restartedExecution = jobLauncherTestUtils.launchJob(jobParameters);

        assertEquals(BatchStatus.COMPLETED, restartedExecution.getStatus());

        assertEquals(
          firstExecution.getJobInstance().getInstanceId(),
          restartedExecution.getJobInstance().getInstanceId()
        );
    }
    
    private void createTestFile(String content) throws IOException {
        Path tempFile = Files.createTempFile("test-data", ".csv");
        Files.write(tempFile, content.getBytes());
        Files.copy(tempFile, inputFile.getFile().toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

}


