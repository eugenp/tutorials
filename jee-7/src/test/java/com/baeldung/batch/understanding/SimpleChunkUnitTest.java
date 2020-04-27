package com.baeldung.batch.understanding;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.BatchStatus;
import javax.batch.runtime.JobExecution;
import javax.batch.runtime.Metric;
import javax.batch.runtime.StepExecution;

import org.junit.jupiter.api.Test;

class SimpleChunkUnitTest {
    @Test
    public void givenChunk_thenBatch_CompletesWithSucess() throws Exception {
        JobOperator jobOperator = BatchRuntime.getJobOperator();
        Long executionId = jobOperator.start("simpleChunk", new Properties());
        JobExecution jobExecution = jobOperator.getJobExecution(executionId);
        jobExecution = BatchTestHelper.keepTestAlive(jobExecution);
        List<StepExecution> stepExecutions = jobOperator.getStepExecutions(executionId);
        for (StepExecution stepExecution : stepExecutions) {
            if (stepExecution.getStepName()
                .equals("firstChunkStep")) {
                Map<Metric.MetricType, Long> metricsMap = BatchTestHelper.getMetricsMap(stepExecution.getMetrics());
                assertEquals(10L, metricsMap.get(Metric.MetricType.READ_COUNT)
                    .longValue());
                assertEquals(10L / 2L, metricsMap.get(Metric.MetricType.WRITE_COUNT)
                    .longValue());
                assertEquals(10L / 3 + (10L % 3 > 0 ? 1 : 0), metricsMap.get(Metric.MetricType.COMMIT_COUNT)
                    .longValue());
            }
        }
        assertEquals(jobExecution.getBatchStatus(), BatchStatus.COMPLETED);
    }
    
    @Test
    public void givenChunk__thenBatch_fetchInformation() throws Exception {
        JobOperator jobOperator = BatchRuntime.getJobOperator();
        Long executionId = jobOperator.start("simpleChunk", new Properties());
        JobExecution jobExecution = jobOperator.getJobExecution(executionId);
        jobExecution = BatchTestHelper.keepTestAlive(jobExecution);
        // job name contains simpleBatchLet which is the name of the file
        assertTrue(jobOperator.getJobNames().contains("simpleChunk"));
        // job parameters are empty
        assertTrue(jobOperator.getParameters(executionId).isEmpty());
        // step execution information
        List<StepExecution> stepExecutions = jobOperator.getStepExecutions(executionId);
        assertEquals("firstChunkStep", stepExecutions.get(0).getStepName());
        // finding out batch status
        assertEquals(BatchStatus.COMPLETED, stepExecutions.get(0).getBatchStatus());
        Map<Metric.MetricType, Long> metricTest = BatchTestHelper.getMetricsMap(stepExecutions.get(0).getMetrics());
        assertEquals(10L, metricTest.get(Metric.MetricType.READ_COUNT).longValue());
        assertEquals(5L, metricTest.get(Metric.MetricType.FILTER_COUNT).longValue());
        assertEquals(4L, metricTest.get(Metric.MetricType.COMMIT_COUNT).longValue());
        assertEquals(5L, metricTest.get(Metric.MetricType.WRITE_COUNT).longValue());
        assertEquals(0L, metricTest.get(Metric.MetricType.READ_SKIP_COUNT).longValue());
        assertEquals(0L, metricTest.get(Metric.MetricType.WRITE_SKIP_COUNT).longValue());
        assertEquals(0L, metricTest.get(Metric.MetricType.PROCESS_SKIP_COUNT).longValue());
        assertEquals(0L, metricTest.get(Metric.MetricType.ROLLBACK_COUNT).longValue());
        assertEquals(jobExecution.getBatchStatus(), BatchStatus.COMPLETED);
    }
}
