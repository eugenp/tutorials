package com.baeldung.nullmatcher;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class MainUnitTest {

    @Mock
    Helper helper;

    @InjectMocks
    Main main;

    @BeforeEach
    void openMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenMethodUnderTest_thenSecondParameterNull() {
        main.methodUnderTest();
        Mockito.verify(helper)
          .concat("Baeldung", null);
    }

    @Test
    void whenMethodUnderTest_thenSecondParameterNullWithMatchers() {
        main.methodUnderTest();
        Mockito.verify(helper)
          .concat(any(), isNull());
    }

}
