package com.baeldung.mutablestring;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.baeldung.mutablestrings.CharsetUsageExample;

public class CharsetUsageUnitTest {

    @Test
    public void givenCharset_whenStringIsEncodedAndDecoded_thenGivesCorrectResult() {
        CharsetUsageExample ch = new CharsetUsageExample();
        String inputString = "hello दुनिया";
        String result = ch.decodeString(ch.encodeString(inputString));
        Assertions.assertEquals(inputString, result);
    }
}
