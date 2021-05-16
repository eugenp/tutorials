package com.baeldung.systemstubs;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import uk.org.webcompere.systemstubs.jupiter.SystemStub;
import uk.org.webcompere.systemstubs.jupiter.SystemStubsExtension;
import uk.org.webcompere.systemstubs.stream.SystemOut;
import uk.org.webcompere.systemstubs.stream.output.NoopStream;

import static uk.org.webcompere.systemstubs.SystemStubs.muteSystemOut;

class OutputMutingUnitTest {
    @Nested
    class MutingWithFacade {
        @Test
        void givenMuteSystemOut() throws Exception {
            muteSystemOut(() -> {
                System.out.println("nothing is output");
            });
        }
    }

    @ExtendWith(SystemStubsExtension.class)
    @Nested
    class MutingWithJUnit5 {
        @SystemStub
        private SystemOut systemOut = new SystemOut(new NoopStream());

        @Test
        void givenMuteSystemOut() throws Exception {
            System.out.println("nothing is output");
        }
    }
}
