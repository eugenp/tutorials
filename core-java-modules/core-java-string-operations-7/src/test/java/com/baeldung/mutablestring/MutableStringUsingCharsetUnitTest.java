package com.baeldung.mutablestring;

import org.junit.Assert;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.baeldung.mutablestrings.MutableStringUsingCharset;

public class MutableStringUsingCharsetUnitTest {
    @Test
    @Disabled
    /**
     * This test is disabled as it works well for Java 8 and below
     */
    public void givenCustomCharSet_whenStringUpdated_StringGetsMutated() throws Exception {
        MutableStringUsingCharset ms = new MutableStringUsingCharset();
        String s = ms.createModifiableString("Hello");
        Assert.assertEquals("Hello", s);
        ms.modifyString();
        Assert.assertEquals("something", s);
    }

}

