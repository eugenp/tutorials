package com.baeldung.junit.norunnablemethods;

import org.junit.Assert;
import org.junit.Test;

public class CustomBaseUnitTest extends BaseUnitTest {

    @Test
    public void givenBaseClassExtended_whenDoTest_thenAssert() {
        String text = formatString("New$World");
        Assert.assertEquals("New_World", text);
    }
}
