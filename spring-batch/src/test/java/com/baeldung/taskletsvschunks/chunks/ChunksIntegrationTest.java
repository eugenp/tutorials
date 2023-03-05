package com.baeldung.taskletsvschunks.chunks;

import com.baeldung.taskletsvschunks.config.ChunksConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringBatchTest
@SpringJUnitConfig(ChunksConfig.class)
public class ChunksIntegrationTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Bean
    JobLauncherTestUtils jobLauncherTestUtils() {
        return new JobLauncherTestUtils();
    }

    @BeforeEach
    public void setup(@Autowired Job jobUnderTest) {
        this.jobLauncherTestUtils.setJob(jobUnderTest); // this is optional if the job is unique
    }

    @Test
    public void givenChunksJob_WhenJobEnds_ThenStatusCompleted() throws Exception {
        JobParameters jobParameters = this.jobLauncherTestUtils.getUniqueJobParameters();
        JobExecution jobExecution = this.jobLauncherTestUtils.launchJob(jobParameters);
        Assert.assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
    }
}