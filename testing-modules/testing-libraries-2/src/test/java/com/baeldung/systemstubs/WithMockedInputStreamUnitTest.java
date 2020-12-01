package com.baeldung.systemstubs;

import org.junit.jupiter.api.Test;
import uk.org.webcompere.systemstubs.stream.input.LinesAltStream;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class WithMockedInputStreamUnitTest {
    @Test
    void givenInputStream_thenCanRead() {
        LinesAltStream testInput = new LinesAltStream("line1", "line2");

        Scanner scanner = new Scanner(testInput);
        assertThat(scanner.nextLine()).isEqualTo("line1");
    }
}
