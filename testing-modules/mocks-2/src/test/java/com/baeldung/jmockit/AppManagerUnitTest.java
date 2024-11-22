package com.baeldung.jmockit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mockit.Mock;
import mockit.MockUp;

public class AppManagerUnitTest {

    private AppManager appManager;

    @BeforeEach
    public void setUp() {
        appManager = new AppManager();
    }

    @Test
    public void givenAppManager_whenStaticMethodCalled_thenValidateExpectedResponse() {
        new MockUp<AppManager>() {
            @Mock
            public boolean isResponsePositive(String value) {
                return false;
            }
        };

        Assertions.assertFalse(appManager.managerResponse("Why are you coming late?"));
    }
}
