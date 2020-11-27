package com.baeldung.systemstubs;

import org.junit.Rule;
import org.junit.Test;
import uk.org.webcompere.systemstubs.rules.SystemExitRule;
import uk.org.webcompere.systemstubs.security.AbortExecutionException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SystemExitJUnit4UnitTest {
    @Rule
    public SystemExitRule systemExitRule = new SystemExitRule();

    @Test
    public void whenAccidentalSystemExit_thenTestFailsRatherThanJVMKilled() {
        // uncomment this to try it
        //System.exit(1);
    }

    @Test
    public void whenExit_thenExitCodeIsAvailable() {
        assertThatThrownBy(() -> {
            System.exit(123);
        }).isInstanceOf(AbortExecutionException.class);

        assertThat(systemExitRule.getExitCode()).isEqualTo(123);
    }
}
