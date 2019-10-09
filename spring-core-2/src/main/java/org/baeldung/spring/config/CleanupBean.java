package org.baeldung.spring.config;

import java.util.concurrent.ExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class CleanupBean implements DisposableBean {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ExecutorService setupExecutor;

    public CleanupBean() {
        super();
    }

    //

    @Override
    public void destroy() {
        logger.info("Starting shutdown process - cleanup");

        setupExecutor.shutdownNow();

        logger.info("Finishing shutdown process - cleanup");
    }

}