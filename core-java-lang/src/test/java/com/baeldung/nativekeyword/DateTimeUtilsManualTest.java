package com.baeldung.nativekeyword;

import static org.junit.Assert.assertNotNull;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;

public class DateTimeUtilsManualTest {

    private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(DateTimeUtilsManualTest.class);

    @BeforeClass
    public static void setUpClass() {
        System.loadLibrary("msvcr100");
        System.loadLibrary("libgcc_s_sjlj-1");
        System.loadLibrary("libstdc++-6");
        System.loadLibrary("nativedatetimeutils");
    }

    @Test
    public void givenNativeLibsLoaded_thenNativeMethodIsAccessible() {
        DateTimeUtils dateTimeUtils = new DateTimeUtils();
        LOG.info("System time is : " + dateTimeUtils.getSystemTime());
        assertNotNull(dateTimeUtils.getSystemTime());
    }
}
