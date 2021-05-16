package com.baeldung.systemstubs;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import uk.org.webcompere.systemstubs.jupiter.SystemStub;
import uk.org.webcompere.systemstubs.jupiter.SystemStubsExtension;
import uk.org.webcompere.systemstubs.stream.SystemIn;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SystemStubsExtension.class)
class SystemInJUnit5UnitTest {
    @SystemStub
    private SystemIn systemIn = new SystemIn("line1", "line2", "line3");

    @Test
    void givenInput_canReadFirstLine() {
        assertThat(new Scanner(System.in).nextLine())
          .isEqualTo("line1");
    }
}
