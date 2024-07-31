package com.baeldung.systemstubs;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import uk.org.webcompere.systemstubs.environment.EnvironmentVariables;
import uk.org.webcompere.systemstubs.jupiter.SystemStub;
import uk.org.webcompere.systemstubs.jupiter.SystemStubsExtension;
import uk.org.webcompere.systemstubs.properties.SystemProperties;

import static com.github.stefanbirkner.systemlambda.SystemLambda.restoreSystemProperties;
import static com.github.stefanbirkner.systemlambda.SystemLambda.withEnvironmentVariable;
import static org.junit.Assert.assertEquals;

@ExtendWith(SystemStubsExtension.class)
class SystemLambdaComparisonUnitTest {
    @SystemStub
    private EnvironmentVariables environmentVariables =
      new EnvironmentVariables("ADDRESS", "https://www.baeldung.com");

    @SystemStub
    private SystemProperties systemProperties = new SystemProperties();

    @Test
    void aSingleSystemLambda() throws Exception {
        restoreSystemProperties(() -> {
            System.setProperty("log_dir", "test/resources");
            assertEquals("test/resources", System.getProperty("log_dir"));
        });
    }

    @Test
    void multipleSystemLambdas() throws Exception {
        restoreSystemProperties(() -> {
            withEnvironmentVariable("URL", "https://www.baeldung.com")
                .execute(() -> {
                    System.setProperty("log_dir", "test/resources");
                    assertEquals("test/resources", System.getProperty("log_dir"));
                    assertEquals("https://www.baeldung.com", System.getenv("URL"));
                });
        });
    }

    @Test
    void multipleSystemStubs() {
        System.setProperty("log_dir", "test/resources");
        assertEquals("test/resources", System.getProperty("log_dir"));
        assertEquals("https://www.baeldung.com", System.getenv("ADDRESS"));
    }
}
