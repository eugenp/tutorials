package com.baeldung.migration.junit5;

import com.baeldung.migration.junit5.extensions.TraceUnitExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(JUnitPlatform.class)
@ExtendWith(TraceUnitExtension.class)
public class RuleExampleUnitTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(RuleExampleUnitTest.class);
    
    @Test
    public void whenTracingTests() {
       LOGGER.debug("This is my test");
        /*...*/
    }
}
