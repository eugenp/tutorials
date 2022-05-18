package com.baeldung.exitcode.exceptionexitgen;

import org.springframework.boot.ExitCodeGenerator;

public class FailedToStartException extends RuntimeException implements ExitCodeGenerator {

    @Override
    public int getExitCode() {
        return 127;
    }
}
