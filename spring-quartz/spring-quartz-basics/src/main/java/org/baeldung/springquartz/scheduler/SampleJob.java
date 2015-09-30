package org.baeldung.springquartz.scheduler;

import org.baeldung.springquartz.service.SampleJobService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
public class SampleJob implements Job {

    Logger _logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SampleJobService jobService;

    public void execute(JobExecutionContext context) throws JobExecutionException {

        _logger.info("Job **{}** fired @ {}", context.getJobDetail().getKey().getName(),
                context.getFireTime());

        jobService.executeSampleJob();

        _logger.info("Next job scheduled @ {}", context.getNextFireTime());
    }
}
