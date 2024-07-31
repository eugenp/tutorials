package com.baeldung.systemstubs;

import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.org.webcompere.systemstubs.SystemStubs.withTextFromSystemIn;

class SystemInExecuteAroundUnitTest {

    @Test
    void givenTextInSystemIn_thenCanReadIt() throws Exception {
        withTextFromSystemIn("line1", "line2", "line3")
          .execute(() -> {
              assertThat(new Scanner(System.in).nextLine())
                .isEqualTo("line1");
          });
    }
}
