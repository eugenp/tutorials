package org.baeldung.spring_batch_partitioner.listener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.StepExecution;

public class JobListener implements JobExecutionListener {

    private static final Log log = LogFactory.getLog(JobListener.class);

    public void beforeJob(JobExecution jobExecution) {

    }

    public void afterJob(JobExecution jobExecution) {
        StringBuilder job = new StringBuilder();
        job.append("\n Job Summary \n");
        job.append("Job: " + jobExecution.getJobInstance().getJobName() + " \n");
        job.append("Begin: " + jobExecution.getStartTime() + "\n");
        job.append("End: " + jobExecution.getEndTime() + "\n");
        job.append("Exit: " + jobExecution.getExitStatus().getExitCode() + "\n");
        job.append("Status: " + jobExecution.getStatus() + "\n");

        job.append("\n Steps Summary \n");
        for (StepExecution stepExecution : jobExecution.getStepExecutions()) {
            job.append("Step: " + stepExecution.getStepName() + " \n");
            job.append("Thread: " + stepExecution.getExecutionContext().get("name") + "\n");
        }
        log.info(job.toString());

    }

}
