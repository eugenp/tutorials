package com.baeldung.migration.junit5;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.migration.junit5.extensions.TraceUnitExtension;

@ExtendWith(TraceUnitExtension.class)
public class RuleExampleUnitTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(RuleExampleUnitTest.class);
    
    @Test
    public void whenTracingTests() {
       LOGGER.debug("This is my test");
        /*...*/
    }
}
