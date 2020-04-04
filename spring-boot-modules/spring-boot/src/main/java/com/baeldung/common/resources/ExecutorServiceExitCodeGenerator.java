package com.baeldung.common.resources;

import org.springframework.boot.ExitCodeGenerator;

import java.util.Objects;
import java.util.concurrent.ExecutorService;

public class ExecutorServiceExitCodeGenerator implements ExitCodeGenerator {

    private ExecutorService executorService;

    public ExecutorServiceExitCodeGenerator(ExecutorService executorService) {
    }

    @Override
    public int getExitCode() {
        try {
            if (!Objects.isNull(executorService)) {
                executorService.shutdownNow();
                return 1;
            }

            return 0;
        } catch (SecurityException ex) {
            return 0;
        }
    }
}
