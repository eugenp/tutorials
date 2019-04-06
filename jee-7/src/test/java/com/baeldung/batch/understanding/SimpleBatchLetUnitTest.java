package com.baeldung.batch.understanding;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Properties;
import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.BatchStatus;
import javax.batch.runtime.JobExecution;
import org.junit.jupiter.api.Test;

class SimpleBatchLetUnitTest {
    @Test
    public void givenBatchLet_thenBatch_CompleteWithSuccess() throws Exception {
        JobOperator jobOperator = BatchRuntime.getJobOperator();
        Long executionId = jobOperator.start("simpleBatchLet", new Properties());
        JobExecution jobExecution = jobOperator.getJobExecution(executionId);
        jobExecution = BatchTestHelper.keepTestAlive(jobExecution);
        assertEquals(jobExecution.getBatchStatus(), BatchStatus.COMPLETED);
    }
    
    @Test
    public void givenBatchLetProperty_thenBatch_CompleteWithSuccess() throws Exception {
        JobOperator jobOperator = BatchRuntime.getJobOperator();
        Long executionId = jobOperator.start("injectionSimpleBatchLet", new Properties());
        JobExecution jobExecution = jobOperator.getJobExecution(executionId);
        jobExecution = BatchTestHelper.keepTestAlive(jobExecution);
        assertEquals(jobExecution.getBatchStatus(), BatchStatus.COMPLETED);
    }
    
    @Test
    public void givenBatchLetPartition_thenBatch_CompleteWithSuccess() throws Exception {
        JobOperator jobOperator = BatchRuntime.getJobOperator();
        Long executionId = jobOperator.start("partitionSimpleBatchLet", new Properties());
        JobExecution jobExecution = jobOperator.getJobExecution(executionId);
        jobExecution = BatchTestHelper.keepTestAlive(jobExecution);
        assertEquals(jobExecution.getBatchStatus(), BatchStatus.COMPLETED);
    }
    
    @Test
    public void givenBatchLetStarted_whenStopped_thenBatchStopped() throws Exception {
        JobOperator jobOperator = BatchRuntime.getJobOperator();
        Long executionId = jobOperator.start("simpleBatchLet", new Properties());
        JobExecution jobExecution = jobOperator.getJobExecution(executionId);
        jobOperator.stop(executionId);
        jobExecution = BatchTestHelper.keepTestStopped(jobExecution);
        assertEquals(jobExecution.getBatchStatus(), BatchStatus.STOPPED);
    }
    
    @Test
    public void givenBatchLetStopped_whenRestarted_thenBatchCompletesSuccess() throws Exception {
        JobOperator jobOperator = BatchRuntime.getJobOperator();
        Long executionId = jobOperator.start("simpleBatchLet", new Properties());
        JobExecution jobExecution = jobOperator.getJobExecution(executionId);
        jobOperator.stop(executionId);
        jobExecution = BatchTestHelper.keepTestStopped(jobExecution);
        assertEquals(jobExecution.getBatchStatus(), BatchStatus.STOPPED);
        executionId = jobOperator.restart(jobExecution.getExecutionId(), new Properties());
        jobExecution = BatchTestHelper.keepTestAlive(jobOperator.getJobExecution(executionId));
        assertEquals(jobExecution.getBatchStatus(), BatchStatus.COMPLETED);
    }
}
