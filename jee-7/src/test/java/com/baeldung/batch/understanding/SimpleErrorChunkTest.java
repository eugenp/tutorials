package com.baeldung.batch.understanding;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Properties;
import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.BatchStatus;
import javax.batch.runtime.JobExecution;

import org.junit.jupiter.api.Test;

class SimpleErrorChunkTest {

    @Test
    public void givenChunkError_thenBatch_CompletesWithFailed() throws Exception {
        JobOperator jobOperator = BatchRuntime.getJobOperator();
        Long executionId = jobOperator.start("simpleErrorChunk", new Properties());
        JobExecution jobExecution = jobOperator.getJobExecution(executionId);
        jobExecution = BatchTestHelper.keepTestFailed(jobExecution);
        System.out.println(jobExecution.getBatchStatus());
        assertEquals(jobExecution.getBatchStatus(), BatchStatus.FAILED);
    }
    
    //@Test
    public void givenChunkError_thenErrorSkipped_CompletesWithSuccess() throws Exception {
        JobOperator jobOperator = BatchRuntime.getJobOperator();
        Long executionId = jobOperator.start("simpleErrorSkipChunk", new Properties());
        JobExecution jobExecution = jobOperator.getJobExecution(executionId);
        jobExecution = BatchTestHelper.keepTestAlive(jobExecution);
        System.out.println(jobExecution.getBatchStatus());
        assertEquals(jobExecution.getBatchStatus(), BatchStatus.COMPLETED);
    }

}
