package com.baeldung.prevent.commandline.application.runner.execution;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
    private static Logger logger = LoggerFactory.getLogger(TaskService.class);

    public void execute(String task) {
        logger.info("do " + task);
    }
}
