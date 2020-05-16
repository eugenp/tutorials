package com.baeldung.powermockito.introduction;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(fullyQualifiedNames = "com.baeldung.powermockito.introduction.*")
public class PowerMockitoUnitTest {

    @Test
    public void givenFinalMethods_whenUsingPowerMockito_thenCorrect() throws Exception {
        CollaboratorWithFinalMethods mock = mock(CollaboratorWithFinalMethods.class);
        whenNew(CollaboratorWithFinalMethods.class).withNoArguments().thenReturn(mock);

        CollaboratorWithFinalMethods collaborator = new CollaboratorWithFinalMethods();
        verifyNew(CollaboratorWithFinalMethods.class).withNoArguments();

        when(collaborator.helloMethod()).thenReturn("Hello Baeldung!");
        String welcome = collaborator.helloMethod();
        verify(collaborator).helloMethod();
        assertEquals("Hello Baeldung!", welcome);
    }

    @Test(expected = RuntimeException.class)
    public void givenStaticMethods_whenUsingPowerMockito_thenCorrect() {
        mockStatic(CollaboratorWithStaticMethods.class);

        when(CollaboratorWithStaticMethods.firstMethod(Mockito.anyString())).thenReturn("Hello Baeldung!");
        when(CollaboratorWithStaticMethods.secondMethod()).thenReturn("Nothing special");
        doThrow(new RuntimeException()).when(CollaboratorWithStaticMethods.class);
        CollaboratorWithStaticMethods.thirdMethod();

        String firstWelcome = CollaboratorWithStaticMethods.firstMethod("Whoever");
        String secondWelcome = CollaboratorWithStaticMethods.firstMethod("Whatever");

        assertEquals("Hello Baeldung!", firstWelcome);
        assertEquals("Hello Baeldung!", secondWelcome);

        verifyStatic(CollaboratorWithStaticMethods.class, times(2));
        CollaboratorWithStaticMethods.firstMethod(Mockito.anyString());

        verifyStatic(CollaboratorWithStaticMethods.class, Mockito.never());
        CollaboratorWithStaticMethods.secondMethod();

        CollaboratorWithStaticMethods.thirdMethod();
    }

    @Test
    public void givenPartialMocking_whenUsingPowerMockito_thenCorrect() throws Exception {
        String returnValue;

        spy(CollaboratorForPartialMocking.class);
        when(CollaboratorForPartialMocking.staticMethod()).thenReturn("I am a static mock method.");
        returnValue = CollaboratorForPartialMocking.staticMethod();
        CollaboratorForPartialMocking.staticMethod();
        assertEquals("I am a static mock method.", returnValue);

        CollaboratorForPartialMocking collaborator = new CollaboratorForPartialMocking();
        CollaboratorForPartialMocking mock = spy(collaborator);

        when(mock.finalMethod()).thenReturn("I am a final mock method.");
        returnValue = mock.finalMethod();
        verify(mock).finalMethod();
        assertEquals("I am a final mock method.", returnValue);

        when(mock, "privateMethod").thenReturn("I am a private mock method.");
        returnValue = mock.privateMethodCaller();
        verifyPrivate(mock).invoke("privateMethod");
        assertEquals("I am a private mock method. Welcome to the Java world.", returnValue);
    }
}