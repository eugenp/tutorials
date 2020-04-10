package org.baeldung.conditionalflow;

import org.baeldung.conditionalflow.config.NumberInfoConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

import java.util.Collection;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBatchTest
@EnableAutoConfiguration
@ContextConfiguration(classes = { NumberInfoConfig.class })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class })
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class DeciderJobIntegrationTest {
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Test
    public void givenNumberGeneratorDecider_whenDeciderRuns_thenStatusIsNotify() throws Exception {
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();
        Collection<StepExecution> actualStepExecutions = jobExecution.getStepExecutions();
        ExitStatus actualJobExitStatus = jobExecution.getExitStatus();

        assertEquals("COMPLETED", actualJobExitStatus.getExitCode()
            .toString());
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
