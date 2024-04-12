package com.baeldung.concurrent.stopexecution;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class SteppedTask implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(SteppedTask.class);

    private List<Step> steps;

    public SteppedTask(List<Step> steps) {
        this.steps = steps;
    }

    @Override
    public void run() {
        LOG.info("running stepped process");
        for (Step step : steps) {
            LOG.info("running step " + step.number);
            try {
                step.perform();
            } catch (InterruptedException e) {
                LOG.info("interrupting task");
                return;
            }
        }
        LOG.info("stepped process finished");
    }
}
