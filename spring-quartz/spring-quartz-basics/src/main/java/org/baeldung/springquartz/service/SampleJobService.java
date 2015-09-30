package org.baeldung.springquartz.service;

import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SampleJobService {

    private Logger _log = LoggerFactory.getLogger(getClass());

    public void executeSampleJob() {

        _log.info("The sample job has begun...");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            _log.error("Error while executing sample job", e);
        } finally {
            _log.info("Sample job has finished...");
        }
    }
}
