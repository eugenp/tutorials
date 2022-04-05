package com.baeldung.systemstubs;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import uk.org.webcompere.systemstubs.jupiter.SystemStub;
import uk.org.webcompere.systemstubs.jupiter.SystemStubsExtension;
import uk.org.webcompere.systemstubs.stream.SystemErr;
import uk.org.webcompere.systemstubs.stream.SystemOut;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SystemStubsExtension.class)
class SystemOutJUnit5UnitTest {
    @SystemStub
    private SystemOut systemOut;

    @SystemStub
    private SystemErr systemErr;

    @Test
    void whenWriteToOutput_thenItCanBeAsserted() {
        System.out.println("to out");
        System.err.println("to err");

        assertThat(systemOut.getLines()).containsExactly("to out");
        assertThat(systemErr.getLines()).containsExactly("to err");
    }
}
