package com.baeldung.migration.junit5;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import com.baeldung.migration.junit5.extensions.TraceUnitExtension;

@RunWith(JUnitPlatform.class)
@ExtendWith(TraceUnitExtension.class)
public class RuleExampleUnitTest {

    @Test
    public void whenTracingTests() {
        System.out.println("This is my test");
        /*...*/
    }
}
