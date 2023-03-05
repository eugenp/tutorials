package com.baeldung.taskletsvschunks.tasklets;

import com.baeldung.taskletsvschunks.config.TaskletsConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(classes = TaskletsConfig.class)
@SpringBatchTest
public class TaskletsIntegrationTest {
    private final JobLauncherTestUtils jobLauncherTestUtils = new JobLauncherTestUtils();

    @Test
    public void givenTaskletsJob_WhenJobEnds_ThenStatusCompleted() throws Exception {
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();
        Assert.assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());
    }
}