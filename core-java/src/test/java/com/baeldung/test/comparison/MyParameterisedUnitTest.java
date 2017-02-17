package com.baeldung.test.comparison;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value = Parameterized.class)
public class MyParameterisedUnitTest {

    private String name;
    private NameCheck nameCheck;

    @Before
    public void initialSetup() {
        nameCheck = new NameCheck();
    }

    public MyParameterisedUnitTest(String myName) {
        this.name = myName;
    }

    @Parameters
    public static Collection<Object[]> data() {
        Object[][] data = new Object[][] { { "Peter" }, { "Sam" }, { "Tim" }, { "Lucy" } };
        return Arrays.asList(data);
    }

    @Test
    public void givenName_whenValidLength_thenTrue() {
        boolean valid = nameCheck.nameCheck(name);
        Assert.assertEquals(valid, true);
    }
}

class NameCheck {

    public boolean nameCheck(String name) {
        if (name.length() > 0)
            return true;
        return false;
    }

}
