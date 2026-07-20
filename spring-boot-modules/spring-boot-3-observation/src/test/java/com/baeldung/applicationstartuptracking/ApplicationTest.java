package com.baeldung.applicationstartuptracking;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(useMainMethod = SpringBootTest.UseMainMethod.ALWAYS)
public class ApplicationTest {

    @Autowired
    private StartupTracker startupTracker;

    @Test
    void test() {
        startupTracker.record();
    }
}
