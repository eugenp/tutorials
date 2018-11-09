package com.baeldung.batch.understanding;

import java.util.HashMap;
import java.util.Map;

import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.BatchStatus;
import javax.batch.runtime.JobExecution;
import javax.batch.runtime.Metric;

public class BatchTestHelper {
    private static final int MAX_TRIES = 40;
    private static final int THREAD_SLEEP = 1000;

    private BatchTestHelper() {
        throw new UnsupportedOperationException();
    }

    public static JobExecution keepTestAlive(JobExecution jobExecution) throws InterruptedException {
        System.out.println(" * Entering keepTestAlive, completed is: " + jobExecution.getBatchStatus()
            .equals(BatchStatus.COMPLETED));

        int maxTries = 0;
        while (!jobExecution.getBatchStatus()
            .equals(BatchStatus.COMPLETED)) {
            if (maxTries < MAX_TRIES) {
                maxTries++;
                Thread.sleep(THREAD_SLEEP);
                jobExecution = BatchRuntime.getJobOperator()
                    .getJobExecution(jobExecution.getExecutionId());
            } else {
                break;
            }
        }
        Thread.sleep(THREAD_SLEEP);

        System.out.println(" * Exiting keepTestAlive, completed is: " + jobExecution.getBatchStatus()
            .equals(BatchStatus.COMPLETED));

        return jobExecution;
    }

    public static JobExecution keepTestFailed(JobExecution jobExecution) throws InterruptedException {
        System.out.println(" * Entering keepTestFailed, STARTED is: " + jobExecution.getBatchStatus()
            .equals(BatchStatus.FAILED));

        int maxTries = 0;
        while (!jobExecution.getBatchStatus()
            .equals(BatchStatus.FAILED)) {
            if (maxTries < MAX_TRIES) {
                maxTries++;
                Thread.sleep(THREAD_SLEEP);
                jobExecution = BatchRuntime.getJobOperator()
                    .getJobExecution(jobExecution.getExecutionId());
            } else {
                break;
            }
        }
        Thread.sleep(THREAD_SLEEP);

        System.out.println(" * Exiting keepTestFailed, completed is: " + jobExecution.getBatchStatus()
            .equals(BatchStatus.FAILED));

        return jobExecution;
    }

    public static JobExecution keepTestStopped(JobExecution jobExecution) throws InterruptedException {
        System.out.println(" * Entering keepTestStopped, STARTED is: " + jobExecution.getBatchStatus()
            .equals(BatchStatus.STOPPED));

        int maxTries = 0;
        while (!jobExecution.getBatchStatus()
            .equals(BatchStatus.STOPPED)) {
            if (maxTries < MAX_TRIES) {
                maxTries++;
                Thread.sleep(THREAD_SLEEP);
                jobExecution = BatchRuntime.getJobOperator()
                    .getJobExecution(jobExecution.getExecutionId());
            } else {
                break;
            }
        }
        Thread.sleep(THREAD_SLEEP);

        System.out.println(" * Exiting keepTestFailed, completed is: " + jobExecution.getBatchStatus()
            .equals(BatchStatus.STOPPED));

        return jobExecution;
    }

    public static Map<Metric.MetricType, Long> getMetricsMap(Metric[] metrics) {
        Map<Metric.MetricType, Long> metricsMap = new HashMap<>();
        for (Metric metric : metrics) {
            metricsMap.put(metric.getType(), metric.getValue());
        }
        return metricsMap;
    }

}
