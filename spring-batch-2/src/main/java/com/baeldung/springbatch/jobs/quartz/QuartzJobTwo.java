package com.baeldung.springbatch.jobs.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class QuartzJobTwo implements Job {

    private static final Logger log = LoggerFactory.getLogger(QuartzJobOne.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            log.info("Job Two is executing from quartz");
        } catch (Exception e) {
            log.error("Error executing Job Two: {}", e.getMessage(), e);
            throw new JobExecutionException(e);
        }
    }
}
