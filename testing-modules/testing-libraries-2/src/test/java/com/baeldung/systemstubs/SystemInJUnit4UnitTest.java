package com.baeldung.systemstubs;

import org.junit.Rule;
import org.junit.Test;
import uk.org.webcompere.systemstubs.rules.SystemInRule;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

public class SystemInJUnit4UnitTest {
    @Rule
    public SystemInRule systemInRule =
      new SystemInRule("line1", "line2", "line3");

    @Test
    public void givenInput_canReadFirstLine() {
        assertThat(new Scanner(System.in).nextLine())
          .isEqualTo("line1");
    }
}
