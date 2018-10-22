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
	        System.out.println(" * Entering keepTestAlive, completed is: " + jobExecution.getBatchStatus().equals(BatchStatus.COMPLETED));
	        
	        int maxTries = 0;
	        while (!jobExecution.getBatchStatus().equals(BatchStatus.COMPLETED)) {
	            if (maxTries < MAX_TRIES) {
	                maxTries++;
	                Thread.sleep(THREAD_SLEEP);
	                jobExecution = BatchRuntime.getJobOperator().getJobExecution(jobExecution.getExecutionId());
	            } else {
	                break;
	            }
	        }
	        Thread.sleep(THREAD_SLEEP);
	        
	        System.out.println(" * Exiting keepTestAlive, completed is: " + jobExecution.getBatchStatus().equals(BatchStatus.COMPLETED));
	        
	        return jobExecution;
	    }

	    /**
	     * Convert the Metric array contained in StepExecution to a key-value map for easy access to Metric parameters.
	     *
	     * @param metrics
	     *         a Metric array contained in StepExecution.
	     *
	     * @return a map view of the metrics array.
	     */
	    public static Map<Metric.MetricType, Long> getMetricsMap(Metric[] metrics) {
	        Map<Metric.MetricType, Long> metricsMap = new HashMap<>();
	        for (Metric metric : metrics) {
	            metricsMap.put(metric.getType(), metric.getValue());
	        }
	        return metricsMap;
	    }

}
