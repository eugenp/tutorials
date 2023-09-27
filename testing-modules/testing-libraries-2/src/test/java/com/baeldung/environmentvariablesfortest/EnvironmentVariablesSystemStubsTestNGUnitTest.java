package com.baeldung.environmentvariablesfortest;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import uk.org.webcompere.systemstubs.environment.EnvironmentVariables;
import uk.org.webcompere.systemstubs.testng.SystemStub;
import uk.org.webcompere.systemstubs.testng.SystemStubsListener;
import static org.assertj.core.api.Assertions.assertThat;

@Listeners(SystemStubsListener.class)
public class EnvironmentVariablesSystemStubsTestNGUnitTest {
    @SystemStub
    private EnvironmentVariables setEnvironment;

    @BeforeClass
    public void beforeAll() {
        setEnvironment.set("testng", "has environment variables");
    }

    @Test
    public void givenEnvironmentVariableWasSet_thenItCanBeRead() {
        assertThat(System.getenv("testng")).isEqualTo("has environment variables");
    }

}
