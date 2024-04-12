package com.baeldung.assume;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assume.assumeFalse;
import static org.junit.Assume.assumeThat;
import static org.junit.Assume.assumeTrue;
import static org.junit.Assume.assumeNotNull;
import static org.junit.Assume.assumeNoException;


import org.junit.Test;

public class ConditionallyIgnoreTestsUnitTest {

    @Test
    public void whenAssumeThatAndOSIsLinux_thenRunTest() {
        assumeThat(getOsName(), is("Linux"));
        assertEquals("run", "RUN".toLowerCase());
    }

    @Test
    public void whenAssumeTrueAndOSIsLinux_thenRunTest() {
        assumeTrue(isExpectedOS(getOsName()));
        assertEquals("run", "RUN".toLowerCase());
    }

    @Test
    public void whenAssumeFalseAndOSIsLinux_thenIgnore() {
        assumeFalse(isExpectedOS(getOsName()));
        assertEquals("run", "RUN".toLowerCase());
    }

    @Test
    public void whenAssumeNotNullAndNotNullOSVersion_thenRun() {
        assumeNotNull(getOsName());
        assertEquals("run", "RUN".toLowerCase());
    }

    /**
     * Let's use a different example here.
     */
    @Test
    public void whenAssumeNoExceptionAndExceptionThrown_thenIgnore() {
        assertEquals("everything ok", "EVERYTHING OK".toLowerCase());
        String t = null;
        try {
          t.charAt(0);
        } catch (NullPointerException npe) {
          assumeNoException(npe);
        }
        assertEquals("run", "RUN".toLowerCase());
    }

    private boolean isExpectedOS(final String osName) {
        return "Linux".equals(osName);
    }

    // This should use System.getProperty("os.name") in a real test.
    private String getOsName() {
        return "Linux";
    }
}