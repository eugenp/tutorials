package com.baeldung.batchtesting;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collection;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.test.AssertFile;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.test.context.ContextConfiguration;

@SpringBatchTest
@EnableAutoConfiguration
@ContextConfiguration(classes = { SpringBatchConfiguration.class })
public class SpringBatchIntegrationTest {

    private static final String TEST_OUTPUT = "src/test/resources/output/actual-output.json";

    private static final String EXPECTED_OUTPUT = "src/test/resources/output/expected-output.json";

    private static final String TEST_INPUT = "src/test/resources/input/test-input.csv";

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;

    @AfterEach
    public void cleanUp() {
        jobRepositoryTestUtils.removeJobExecutions();
    }

    private JobParameters defaultJobParameters() {
        JobParametersBuilder paramsBuilder = new JobParametersBuilder();
        paramsBuilder.addString("file.input", TEST_INPUT);
        paramsBuilder.addString("file.output", TEST_OUTPUT);
        return paramsBuilder.toJobParameters();
    }

    @Test
    public void givenReferenceOutput_whenJobExecuted_thenSuccess() throws Exception {
        // given
        FileSystemResource expectedResult = new FileSystemResource(EXPECTED_OUTPUT);
        FileSystemResource actualResult = new FileSystemResource(TEST_OUTPUT);

        // when
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(defaultJobParameters());
        JobInstance actualJobInstance = jobExecution.getJobInstance();
        ExitStatus actualJobExitStatus = jobExecution.getExitStatus();

        // then
        assertEquals("transformBooksRecords", actualJobInstance.getJobName());
        assertEquals("COMPLETED", actualJobExitStatus.getExitCode());
        AssertFile.assertFileEquals(expectedResult, actualResult);
    }

    @Test
    public void givenReferenceOutput_whenStep1Executed_thenSuccess() throws Exception {

        // given
        FileSystemResource expectedResult = new FileSystemResource(EXPECTED_OUTPUT);
        FileSystemResource actualResult = new FileSystemResource(TEST_OUTPUT);

        // when
        JobExecution jobExecution = jobLauncherTestUtils.launchStep("step1", defaultJobParameters());
        Collection<StepExecution> actualStepExecutions = jobExecution.getStepExecutions();
        ExitStatus actualJobExitStatus = jobExecution.getExitStatus();

        // then
        assertEquals(1, actualStepExecutions.size());
        assertEquals("COMPLETED", actualJobExitStatus.getExitCode());
        AssertFile.assertFileEquals(expectedResult, actualResult);
    }

    @Test
    public void whenStep2Executed_thenSuccess() {

        // when
        JobExecution jobExecution = jobLauncherTestUtils.launchStep("step2", defaultJobParameters());
        Collection<StepExecution> actualStepExecutions = jobExecution.getStepExecutions();
        ExitStatus actualExitStatus = jobExecution.getExitStatus();

        // then
        assertEquals(1, actualStepExecutions.size());
        assertEquals("COMPLETED", actualExitStatus.getExitCode());
        actualStepExecutions.forEach(stepExecution -> {
            assertEquals(8L, stepExecution.getWriteCount());
        });
    }

}
