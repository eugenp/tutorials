package com.baeldung.maven.properties;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NonAsciiStringUnitTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(NonAsciiStringUnitTest.class);
    
    /**
     * Sanity check to ensure that the code is still able to compile and run
     */
    @Test
    public void whenNonAsciiStringIsUsed_thenCodeRuns() {
        String nonAsciiStr = NonAsciiString.getNonAsciiString();
        LOGGER.info(nonAsciiStr);
    }
    
}
