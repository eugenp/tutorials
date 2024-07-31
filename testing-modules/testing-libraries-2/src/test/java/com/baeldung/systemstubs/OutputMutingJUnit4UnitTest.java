package com.baeldung.systemstubs;

import org.junit.Rule;
import org.junit.Test;
import uk.org.webcompere.systemstubs.rules.SystemOutRule;
import uk.org.webcompere.systemstubs.stream.output.NoopStream;

public class OutputMutingJUnit4UnitTest {
    @Rule
    public SystemOutRule systemOutRule = new SystemOutRule(new NoopStream());

    @Test
    public void givenMuteSystemOut() throws Exception {
        System.out.println("nothing is output");
    }
}
