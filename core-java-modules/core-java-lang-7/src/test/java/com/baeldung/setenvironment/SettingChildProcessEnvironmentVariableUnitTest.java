package com.baeldung.setenvironment;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.Map;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

class SettingChildProcessEnvironmentVariableUnitTest {

    public static final String ENVIRONMENT_VARIABLE_NAME = "test";
    public static final String ENVIRONMENT_VARIABLE_VALUE = "Hello World";
    public static final String CHILD_PROCESS_CONDITION = "CHILD_PROCESS_TEST";
    public static final String CHILD_PROCESS_VALUE = "true";
    public static final String CHILD_PROCESS_TAG = "child_process";
    public static final String TAG = String.format("-Dgroups=%s", CHILD_PROCESS_TAG);
    private final String testClass = String.format("-Dtest=%s", getClass().getName());
    // to run on windows the first argument should be "mvn.cmd"
    private final String[] arguments = {"mvn", "test", TAG, testClass};

    @Test
    void givenChildProcessTestRunner_whenRunTheTest_thenAllSucceed()
      throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.inheritIO();

        Map<String, String> environment = processBuilder.environment();
        environment.put(CHILD_PROCESS_CONDITION, CHILD_PROCESS_VALUE);
        environment.put(ENVIRONMENT_VARIABLE_NAME, ENVIRONMENT_VARIABLE_VALUE);
        Process process = processBuilder.command(arguments).start();

        int errorCode = process.waitFor();
        assertThat(errorCode).isZero();
    }

    @Test
    @EnabledIfEnvironmentVariable(named = CHILD_PROCESS_CONDITION, matches = CHILD_PROCESS_VALUE)
    @Tag(CHILD_PROCESS_TAG)
    void givenChildProcess_whenGetEnvironmentVariable_thenReturnsCorrectValue() {
        String actual = System.getenv(ENVIRONMENT_VARIABLE_NAME);
        assertThat(actual).isEqualTo(ENVIRONMENT_VARIABLE_VALUE);
    }
}
