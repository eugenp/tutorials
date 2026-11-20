package com.baeldung.process.id;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ProcessIdUtilUnitTest {

    @Test
    void givenJvmNameWithAtSymbol_whenParsingPid_thenReturnsPidBeforeAt() {
        String jvmName = "12345@hostname";

        String pid = ProcessIdUtil.parsePidFromName(jvmName);

        assertThat(pid).isEqualTo("12345");
    }

    @Test
    void givenJvmNameWithEmptyHost_whenParsingPid_thenReturnsPid() {
        String jvmName = "999@";

        String pid = ProcessIdUtil.parsePidFromName(jvmName);

        assertThat(pid).isEqualTo("999");
    }

    @Test
    void givenJvmNameWithoutAtSymbol_whenParsingPid_thenReturnsNull() {
        String jvmName = "no-at-sign";

        String pid = ProcessIdUtil.parsePidFromName(jvmName);

        assertThat(pid).isNull();
    }

    @Test
    void givenJvmNameStartingWithAt_whenParsingPid_thenReturnsNull() {
        String jvmName = "@hostname";

        String pid = ProcessIdUtil.parsePidFromName(jvmName);

        assertThat(pid).isNull();
    }

    @Test
    void givenNullJvmName_whenParsingPid_thenReturnsNull() {
        String pid = ProcessIdUtil.parsePidFromName(null);

        assertThat(pid).isNull();
    }

    @Test
    void givenRuntimeMxBeanName_whenGettingPreJava9Pid_thenReturnsNumericString() {
        String pid = ProcessIdUtil.getProcessIdFromJvmName();

        assertThat(pid).isNotNull();
        assertThat(pid).matches("\\d+");
    }

    @Test
    void givenRuntimeMXBean_whenGettingPid_thenReturnsPositiveValue() {
        long pid = ProcessIdUtil.getProcessIdFromRuntimeMXBean();

        assertThat(pid).isPositive();
    }

    @Test
    void givenProcessHandle_whenGettingPid_thenReturnsPositiveValue() {
        long pid = ProcessIdUtil.getProcessIdFromProcessHandle();

        assertThat(pid).isPositive();
    }

    @Test
    void givenCurrentProcess_whenGettingPid_thenReturnsConsistentValue() {
        long firstCall = ProcessIdUtil.getProcessId();
        long secondCall = ProcessIdUtil.getProcessId();

        assertThat(firstCall).isEqualTo(secondCall).isPositive();
    }
}
