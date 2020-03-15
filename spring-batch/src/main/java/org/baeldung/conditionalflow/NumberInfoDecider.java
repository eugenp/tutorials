package org.baeldung.conditionalflow;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;

public class NumberInfoDecider implements JobExecutionDecider {

    @Override
    public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
        if(jobExecution.getExitStatus().equals("UNKNOWN")) {
            return new FlowExecutionStatus("NOTIFY");
        } else {
            return null;
        }
    }
}
