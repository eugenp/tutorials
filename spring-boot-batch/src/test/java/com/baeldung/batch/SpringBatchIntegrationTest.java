package com.baeldung.batch;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.AssertFile;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.StepScopeTestExecutionListener;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

@RunWith(SpringRunner.class)
@SpringBatchTest
@EnableAutoConfiguration
@ContextConfiguration(classes = { SpringBatchConfiguration.class })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, StepScopeTestExecutionListener.class , DirtiesContextTestExecutionListener.class})
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class SpringBatchIntegrationTest {

    private static final String TEST_OUTPUT = "src/test/resources/output/actual-output.json";

    private static final String EXPECTED_OUTPUT = "src/test/resources/output/expected-output.json";

    private static final String TEST_INPUT = "src/test/resources/input/test-input.csv";

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;
    
    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;
    
    @After
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

        // then
        // @formatter:off
        assertThat(jobExecution.getJobInstance().getJobName(), is("transformBooksRecords"));
        assertThat(jobExecution.getExitStatus().getExitCode(), is("COMPLETED"));
        AssertFile.assertFileEquals(expectedResult, actualResult);
        // @formatter:on
        jobExecution.stop();
    }

    @Test
    public void givenReferenceOutput_whenStep1Executed_thenSuccess() throws Exception {

        // given
        FileSystemResource expectedResult = new FileSystemResource(EXPECTED_OUTPUT);
        FileSystemResource actualResult = new FileSystemResource(TEST_OUTPUT);

        // when
        JobExecution jobExecution = jobLauncherTestUtils.launchStep("step1", defaultJobParameters());

        // then
        // @formatter:off
        assertThat(jobExecution.getStepExecutions().size(), is(1));
        assertThat(jobExecution.getExitStatus().getExitCode(), is("COMPLETED"));
        AssertFile.assertFileEquals(expectedResult, actualResult);
        // @formatter:on
    }

    @Test
    public void whenStep2Executed_thenSuccess() {

        // when
        JobExecution jobExecution = jobLauncherTestUtils.launchStep("step2", defaultJobParameters());

        // then
        // @formatter:off
        assertThat(jobExecution.getStepExecutions().size(), is(1));
        assertThat(jobExecution.getExitStatus().getExitCode(), is("COMPLETED"));
        jobExecution.getStepExecutions()
            .forEach(stepExecution -> {
                assertThat(stepExecution.getWriteCount(), is(8));
            });
        // @formatter:on
    }

}
