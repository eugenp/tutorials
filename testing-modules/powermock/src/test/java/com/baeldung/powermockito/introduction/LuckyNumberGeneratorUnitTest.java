package com.baeldung.powermockito.introduction;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;
import static org.powermock.api.mockito.PowerMockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(fullyQualifiedNames = "com.baeldung.powermockito.introduction.LuckyNumberGenerator")
public class LuckyNumberGeneratorUnitTest {

    @Test
    public final void givenPrivateMethodWithReturn_whenUsingPowerMockito_thenCorrect() throws Exception {
        LuckyNumberGenerator mock = spy(new LuckyNumberGenerator());

        when(mock, "getDefaultLuckyNumber").thenReturn(300);

        int result = mock.getLuckyNumber(null);

        assertEquals(300, result);
    }

    @Test
    public final void givenPrivateMethodWithArgumentAndReturn_whenUsingPowerMockito_thenCorrect() throws Exception {
        LuckyNumberGenerator mock = spy(new LuckyNumberGenerator());

        doReturn(1).when(mock, "getComputedLuckyNumber", ArgumentMatchers.anyInt());

        int result = mock.getLuckyNumber("Jack");

        assertEquals(1, result);
    }

    @Test
    public final void givenPrivateMethodWithNoArgumentAndReturn_whenUsingPowerMockito_thenCorrect() throws Exception {
        LuckyNumberGenerator mock = spy(new LuckyNumberGenerator());

        int result = mock.getLuckyNumber("Tyranosorous");

        verifyPrivate(mock).invoke("saveIntoDatabase", ArgumentMatchers.anyString());
        assertEquals(10000, result);
    }

}
