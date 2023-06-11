package com.baeldung.variableinitialization;

import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;

import com.baeldung.variableInitialization.A;
import com.baeldung.variableInitialization.B;

public class AUnitTest {

    @Test
    public void whenCreatingTest_useDependencyInjection() {
        // given
        B b = mock(B.class);
        A a = new A(b);
    }
}
