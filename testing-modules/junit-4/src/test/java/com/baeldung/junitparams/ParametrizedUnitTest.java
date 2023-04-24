package com.baeldung.junitparams;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

@RunWith(value = Parameterized.class)
public class ParametrizedUnitTest {

    private int value;
    private boolean isEven;

    public ParametrizedUnitTest(int value, boolean isEven) {
        this.value = value;
        this.isEven = isEven;
    }

    @Parameters
    public static Collection<Object[]> data() {
        Object[][] data = new Object[][]{{1, false}, {2, true}, {4, true}};
        return Arrays.asList(data);
    }

    @Test
    public void givenParametrizedNumber_ifEvenCheckOK_thenCorrect() {
        Assert.assertEquals(isEven, value % 2 == 0);
    }
}
