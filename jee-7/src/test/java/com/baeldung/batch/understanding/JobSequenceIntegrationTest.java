package com.baeldung.batch.understanding;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.BatchStatus;
import javax.batch.runtime.JobExecution;
import javax.batch.runtime.StepExecution;

import org.junit.jupiter.api.Test;

class JobSequenceIntegrationTest {
    @Test
    public void givenTwoSteps_thenBatch_CompleteWithSuccess() throws Exception {
        JobOperator jobOperator = BatchRuntime.getJobOperator();
        Long executionId = jobOperator.start("simpleJobSequence", new Properties());
        JobExecution jobExecution = jobOperator.getJobExecution(executionId);
        jobExecution = BatchTestHelper.keepTestAlive(jobExecution);
        assertEquals(2 , jobOperator.getStepExecutions(executionId).size());
        assertEquals(jobExecution.getBatchStatus(), BatchStatus.COMPLETED);
    }
    
    @Test
    public void givenFlow_thenBatch_CompleteWithSuccess() throws Exception {
        JobOperator jobOperator = BatchRuntime.getJobOperator();
        Long executionId = jobOperator.start("flowJobSequence", new Properties());
        JobExecution jobExecution = jobOperator.getJobExecution(executionId);
        jobExecution = BatchTestHelper.keepTestAlive(jobExecution);
        assertEquals(3 , jobOperator.getStepExecutions(executionId).size());
        assertEquals(jobExecution.getBatchStatus(), BatchStatus.COMPLETED);
    }
    
    @Test
    public void givenDecider_thenBatch_CompleteWithSuccess() throws Exception {
        JobOperator jobOperator = BatchRuntime.getJobOperator();
        Long executionId = jobOperator.start("decideJobSequence", new Properties());
        JobExecution jobExecution = jobOperator.getJobExecution(executionId);
        jobExecution = BatchTestHelper.keepTestAlive(jobExecution);
        List<StepExecution> stepExecutions = jobOperator.getStepExecutions(executionId);
        List<String> executedSteps = new ArrayList<>();
        for (StepExecution stepExecution : stepExecutions) {
            executedSteps.add(stepExecution.getStepName());
        }
        assertEquals(2, jobOperator.getStepExecutions(executionId).size());
        assertArrayEquals(new String[] { "firstBatchStepStep1", "firstBatchStepStep3" }, executedSteps.toArray());
        assertEquals(jobExecution.getBatchStatus(), BatchStatus.COMPLETED);
    }
    
    @Test
    public void givenSplit_thenBatch_CompletesWithSuccess() throws Exception {
        JobOperator jobOperator = BatchRuntime.getJobOperator();
        Long executionId = jobOperator.start("splitJobSequence", new Properties());
        JobExecution jobExecution = jobOperator.getJobExecution(executionId);
        jobExecution = BatchTestHelper.keepTestAlive(jobExecution);
        List<StepExecution> stepExecutions = jobOperator.getStepExecutions(executionId);
        List<String> executedSteps = new ArrayList<>();
        for (StepExecution stepExecution : stepExecutions) {
            executedSteps.add(stepExecution.getStepName());
        }
        assertEquals(3, stepExecutions.size());
        assertTrue(executedSteps.contains("splitJobSequenceStep1"));
        assertTrue(executedSteps.contains("splitJobSequenceStep2"));
        assertTrue(executedSteps.contains("splitJobSequenceStep3"));
        assertTrue(executedSteps.get(0).equals("splitJobSequenceStep1") || executedSteps.get(0).equals("splitJobSequenceStep2"));
        assertTrue(executedSteps.get(1).equals("splitJobSequenceStep1") || executedSteps.get(1).equals("splitJobSequenceStep2"));
        assertTrue(executedSteps.get(2).equals("splitJobSequenceStep3"));
        assertEquals(jobExecution.getBatchStatus(), BatchStatus.COMPLETED);
    }
}
