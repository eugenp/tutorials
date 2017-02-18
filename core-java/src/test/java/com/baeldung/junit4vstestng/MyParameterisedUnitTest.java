package com.baeldung.junit4vstestng;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

@RunWith(value = Parameterized.class)
public class MyParameterisedUnitTest {

    private String name;

    public MyParameterisedUnitTest(String myName) {
        this.name = myName;
    }

    @Parameters
    public static Collection<Object[]> data() {
        Object[][] data = new Object[][]{{"Peter"}, {"Sam"}, {"Tim"}, {"Lucy"}};
        return Arrays.asList(data);
    }

    @Test
    public void givenName_whenValidLength_thenTrue() {
        boolean valid = name.length() > 0;
        Assert.assertEquals(valid, true);
    }
}
