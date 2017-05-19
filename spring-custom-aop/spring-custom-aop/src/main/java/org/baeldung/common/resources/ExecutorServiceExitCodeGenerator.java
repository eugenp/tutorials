package org.baeldung.common.resources;

import java.util.Objects;
import java.util.concurrent.ExecutorService;

import org.springframework.boot.ExitCodeGenerator;

public class ExecutorServiceExitCodeGenerator implements ExitCodeGenerator {

    private ExecutorService executorService;

    public ExecutorServiceExitCodeGenerator(ExecutorService executorService) {
    }

    @Override
    public int getExitCode() {
        int returnCode = 0;
        try {
            if (!Objects.isNull(executorService)) {
                executorService.shutdownNow();
                returnCode = 1;
            }
        } catch (SecurityException ex) {
            returnCode = 0;
        }
        return returnCode;
    }

}
