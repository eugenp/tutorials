package com.baeldung.bootbatch;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringBatchTest
@DirtiesContext
@SpringJUnitConfig(BatchConfiguration.class)
@PropertySource("classpath:application.properties")
@EnableAutoConfiguration
@ExtendWith({ OutputCaptureExtension.class })
public class SpringBootBatchIntegrationTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;

    @MockBean
    private JobCompletionNotificationListener jobCompletionNotificationListener;

    @AfterEach
    public void cleanUp() {
        jobRepositoryTestUtils.removeJobExecutions();
    }

    @Test
    public void givenCoffeeList_whenJobExecuted_thenSuccess(CapturedOutput output) throws Exception {
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();
        JobInstance jobInstance = jobExecution.getJobInstance();
        ExitStatus jobExitStatus = jobExecution.getExitStatus();
        assertEquals("importUserJob", jobInstance.getJobName());
        assertEquals("COMPLETED", jobExitStatus.getExitCode());

        String regex = "\\[virtual-thread-executor\\d+\\] INFO  com.baeldung.bootbatch.CoffeeItemProcessor";
        assertThat(output.getAll()).containsPattern(regex);
    }
}
