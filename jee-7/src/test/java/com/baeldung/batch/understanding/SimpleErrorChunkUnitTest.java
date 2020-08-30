package com.baeldung.batch.understanding;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Properties;

import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.BatchStatus;
import javax.batch.runtime.JobExecution;
import javax.batch.runtime.StepExecution;

import org.junit.jupiter.api.Test;

class SimpleErrorChunkUnitTest {

    @Test
    public void givenChunkError_thenBatch_CompletesWithFailed() throws Exception {
        JobOperator jobOperator = BatchRuntime.getJobOperator();
        Long executionId = jobOperator.start("simpleErrorChunk", new Properties());
        JobExecution jobExecution = jobOperator.getJobExecution(executionId);
        jobExecution = BatchTestHelper.keepTestFailed(jobExecution);
        assertEquals(jobExecution.getBatchStatus(), BatchStatus.FAILED);
    }

    @Test
    public void givenChunkError_thenErrorSkipped_CompletesWithSuccess() throws Exception {
        JobOperator jobOperator = BatchRuntime.getJobOperator();
        Long executionId = jobOperator.start("simpleErrorSkipChunk", new Properties());
        JobExecution jobExecution = jobOperator.getJobExecution(executionId);
        jobExecution = BatchTestHelper.keepTestAlive(jobExecution);
        List<StepExecution> stepExecutions = jobOperator.getStepExecutions(executionId);
        for (StepExecution stepExecution : stepExecutions) {
            if (stepExecution.getStepName()
                .equals("errorStep")) {
                jobOperator.getStepExecutions(executionId)
                .stream()
                .map(BatchTestHelper::getProcessSkipCount)
                .forEach(skipCount -> assertEquals(1L, skipCount.longValue()));
            }
        }
        assertEquals(jobExecution.getBatchStatus(), BatchStatus.COMPLETED);
    }
}
