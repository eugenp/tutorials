package com.baeldung.batch.understanding;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Map;
import java.util.Properties;
import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.BatchStatus;
import javax.batch.runtime.JobExecution;
import javax.batch.runtime.Metric;
import javax.batch.runtime.StepExecution;
import com.baeldung.batch.understanding.BatchTestHelper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;

@Disabled("Should be fixed in BAEL-3812")
class CustomCheckPointUnitTest {
    @Test
    public void givenChunk_whenCustomCheckPoint_thenCommitCountIsThree() throws Exception {
        JobOperator jobOperator = BatchRuntime.getJobOperator();
        Long executionId = jobOperator.start("customCheckPoint", new Properties());
        JobExecution jobExecution = jobOperator.getJobExecution(executionId);
        jobExecution = BatchTestHelper.keepTestAlive(jobExecution);
        for (StepExecution stepExecution : jobOperator.getStepExecutions(executionId)) {
            if (stepExecution.getStepName()
                .equals("firstChunkStep")) {
                jobOperator.getStepExecutions(executionId)
                    .stream()
                    .map(BatchTestHelper::getCommitCount)
                    .forEach(count -> assertEquals(3L, count.longValue()));
            }
        }
        assertEquals(jobExecution.getBatchStatus(), BatchStatus.COMPLETED);
    }
}
