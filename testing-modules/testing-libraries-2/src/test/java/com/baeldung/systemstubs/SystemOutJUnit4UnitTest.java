package com.baeldung.systemstubs;

import org.junit.Rule;
import org.junit.Test;
import uk.org.webcompere.systemstubs.rules.SystemErrRule;
import uk.org.webcompere.systemstubs.rules.SystemOutRule;

import static org.assertj.core.api.Assertions.assertThat;

public class SystemOutJUnit4UnitTest {
    @Rule
    public SystemOutRule systemOutRule = new SystemOutRule();

    @Rule
    public SystemErrRule systemErrRule = new SystemErrRule();

    @Test
    public void whenCodeWritesToSystemOut_itCanBeRead() {
        System.out.println("line1");
        System.out.println("line2");

        assertThat(systemOutRule.getLines())
          .containsExactly("line1", "line2");
    }

    @Test
    public void whenCodeWritesToSystemOut_itCanBeReadAsText() {
        System.out.println("line1");
        System.out.println("line2");

        assertThat(systemOutRule.getText())
          .startsWith("line1");
    }

    @Test
    public void whenCodeWritesToSystemOut_itCanBeReadAsNormalizedLines() {
        System.out.println("line1");
        System.out.println("line2");

        assertThat(systemOutRule.getLinesNormalized())
          .isEqualTo("line1\nline2\n");
    }

    @Test
    public void whenCodeWritesToSystemErr_itCanBeRead() {
        System.err.println("line1");
        System.err.println("line2");

        assertThat(systemErrRule.getLines())
                .containsExactly("line1", "line2");
    }
}
