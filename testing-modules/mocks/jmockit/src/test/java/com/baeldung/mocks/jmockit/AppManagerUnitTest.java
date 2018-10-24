package com.baeldung.mocks.jmockit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mockit.Deencapsulation;
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

    @Test
    public void givenAppManager_whenPrivateStaticMethod_thenValidateExpectedResponse() {
        final int response = Deencapsulation.invoke(AppManager.class, "stringToInteger", "110");
        Assertions.assertEquals(110, response);
    }

    @Test
    public void givenAppManager_whenPrivateStaticMethod_thenExpectException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Deencapsulation.invoke(AppManager.class, "stringToInteger", "11r");
        });
    }
}
