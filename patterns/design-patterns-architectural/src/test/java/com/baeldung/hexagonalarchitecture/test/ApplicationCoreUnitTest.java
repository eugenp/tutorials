package com.baeldung.hexagonalarchitecture.test;

import com.baeldung.hexagonalarchitecture.ApplicationCore;
import org.junit.Assert;
import org.junit.Test;

public class ApplicationCoreUnitTest {
    @Test
    public void logSaves() {
        FakeTestLogger fakeLogger = new FakeTestLogger();
        ApplicationCore core = new ApplicationCore(fakeLogger);
        core.run();
        Assert.assertTrue(fakeLogger.contains("Core Process Is Running"));
    }
}
