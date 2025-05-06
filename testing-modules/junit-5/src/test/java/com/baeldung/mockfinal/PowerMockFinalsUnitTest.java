package com.baeldung.mockfinal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({MyList.class, FinalList.class})
public class PowerMockFinalsUnitTest {

    @Test
    public void whenMockFinalMethod_thenMockWorks() throws Exception {
        MyList mockClass = PowerMockito.mock(MyList.class);
        when(mockClass.finalMethod()).thenReturn(1);

        assertThat(mockClass.finalMethod()).isNotZero();
    }

    @Test
    public void whenMockFinalClass_thenMockWorks() throws Exception {
        FinalList mockClass = PowerMockito.mock(FinalList.class);
        when(mockClass.size()).thenReturn(2);

        assertThat(mockClass.size()).isNotEqualTo(1);
    }

}
