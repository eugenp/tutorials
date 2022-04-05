package org.baeldung.conditionalflow;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;

public class NumberInfoDecider implements JobExecutionDecider {

    public static final String NOTIFY = "NOTIFY";
    public static final String QUIET = "QUIET";

    /**
     * Method that determines notification status of job
     * @return true if notifications should be sent.
     */
    private boolean shouldNotify() {
        return true;
    }

    @Override
    public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
        if (shouldNotify()) {
            return new FlowExecutionStatus(NOTIFY);
        } else {
            return new FlowExecutionStatus(QUIET);
        }
    }
}
