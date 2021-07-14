package com.baeldung.systemstubs;

import org.junit.jupiter.api.Test;
import uk.org.webcompere.systemstubs.properties.SystemProperties;
import uk.org.webcompere.systemstubs.stream.SystemOut;
import uk.org.webcompere.systemstubs.stream.output.DisallowWriteStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static uk.org.webcompere.systemstubs.SystemStubs.tapSystemOutNormalized;
import static uk.org.webcompere.systemstubs.resource.Resources.with;

class SystemOutAndErrExecuteAroundUnitTest {
    @Test
    void givenTapOutput_thenGetOutput() throws Exception {
        String output = tapSystemOutNormalized(() -> {
            System.out.println("a");
            System.out.println("b");
        });

        assertThat(output).isEqualTo("a\nb\n");
    }

    @Test
    void givenCaptureOutputWithSystemOut_thenGetOutput() throws Exception {
        SystemOut systemOut = new SystemOut();
        SystemProperties systemProperties = new SystemProperties("a", "!");
        with(systemOut, systemProperties)
          .execute(()  -> {
            System.out.println("a: " + System.getProperty("a"));
        });

        assertThat(systemOut.getLines()).containsExactly("a: !");
    }

    @Test
    void givenCannotWrite_thenWritingIsError() {
        assertThatThrownBy(() -> {
            new SystemOut(new DisallowWriteStream())
              .execute(() -> System.out.println("boo"));
            }).isInstanceOf(AssertionError.class);
    }
}
