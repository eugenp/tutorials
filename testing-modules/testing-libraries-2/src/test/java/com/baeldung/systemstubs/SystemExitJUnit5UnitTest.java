package com.baeldung.systemstubs;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import uk.org.webcompere.systemstubs.jupiter.SystemStub;
import uk.org.webcompere.systemstubs.jupiter.SystemStubsExtension;
import uk.org.webcompere.systemstubs.security.AbortExecutionException;
import uk.org.webcompere.systemstubs.security.SystemExit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(SystemStubsExtension.class)
class SystemExitJUnit5UnitTest {
    @SystemStub
    private SystemExit systemExit;

    @Test
    void whenExit_thenExitCodeIsAvailable() {
        assertThatThrownBy(() -> {
            System.exit(123);
        }).isInstanceOf(AbortExecutionException.class);

        assertThat(systemExit.getExitCode()).isEqualTo(123);
    }
}
