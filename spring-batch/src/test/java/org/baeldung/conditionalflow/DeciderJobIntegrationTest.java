package org.baeldung.conditionalflow;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.baeldung.conditionalflow.config.NumberInfoConfig;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.test.context.ContextConfiguration;

import java.util.Collection;
import java.util.Iterator;

@SpringBatchTest
@EnableAutoConfiguration
@ContextConfiguration(classes = { NumberInfoConfig.class })
public class DeciderJobIntegrationTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Test
    public void givenNumberGeneratorDecider_whenDeciderRuns_thenStatusIsNotify() throws Exception {
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();
        Collection<StepExecution> actualStepExecutions = jobExecution.getStepExecutions();
        ExitStatus actualJobExitStatus = jobExecution.getExitStatus();

        assertEquals("COMPLETED", actualJobExitStatus.getExitCode());
        assertEquals(2, actualStepExecutions.size());
        boolean notifyStepDidRun = false;
        Iterator<StepExecution> iterator = actualStepExecutions.iterator();
        while (iterator.hasNext() && !notifyStepDidRun) {
            if (iterator.next()
                .getStepName()
                .equals("Notify step")) {
                notifyStepDidRun = true;
            }
        }
        assertTrue(notifyStepDidRun);
    }
}
