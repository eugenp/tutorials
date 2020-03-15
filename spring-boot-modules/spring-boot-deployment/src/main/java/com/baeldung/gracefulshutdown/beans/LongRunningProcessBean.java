package com.baeldung.gracefulshutdown.beans;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

@Component
public class LongRunningProcessBean {

    private static final Logger LOG = LoggerFactory.getLogger(LongRunningProcessBean.class);

    @Autowired
    private TaskExecutor taskExecutor;

    @PostConstruct
    public void runTaskOnStartup() {
        LOG.info("runTaskOnStartup entering");
        for (int i = 0; i < 3; i++) {
            final int processNumber = i;
            taskExecutor.execute(() -> {
                try {
                    LOG.info("Long running process {} using threadpool started", processNumber);
                    Thread.sleep(60_000);
                    LOG.info("Long running process {} using threadpool completed", processNumber);
                } catch (Exception e) {
                    LOG.error("Error while executing task", e);
                }
            });
        }
        LOG.info("runTaskOnStartup exiting");
    }
}
