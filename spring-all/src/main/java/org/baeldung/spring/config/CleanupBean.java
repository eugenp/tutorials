package org.baeldung.spring.config;

import java.util.concurrent.ExecutorService;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class CleanupBean implements DisposableBean {

    @Autowired
    private ExecutorService setupExecutor;

    public CleanupBean() {
        super();
    }

    //

    @Override
    public void destroy() {
        setupExecutor.shutdownNow();
    }

}