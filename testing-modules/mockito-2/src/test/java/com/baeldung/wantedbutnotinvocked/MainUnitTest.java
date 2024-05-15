package com.baeldung.wantedbutnotinvocked;

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
    Main main = new Main();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void givenValueUpperThan5_WhenMethodUnderTest_ThenDelegatesToHelperClass() {
        main.methodUnderTest(7);
        Mockito.verify(helper)
          .getBaeldungString();
    }

    // Uncomment the next line to see the error
    // @Test
    void givenValueLowerThan5_WhenMethodUnderTest_ThenDelegatesToGetBaeldungString() {
        main.methodUnderTest(3);
        Mockito.verify(helper)
          .getBaeldungString();
    }

}
