package com.baeldung.text;

import org.apache.commons.text.translate.UnicodeEscaper;
import org.junit.Assert;
import org.junit.Test;

public class UnicodeEscaperUnitTest {

    @Test
    public void whenTranslate_thenCorrect() {
        UnicodeEscaper ue = UnicodeEscaper.above(0);
        String result = ue.translate("ABCD");

        Assert.assertEquals("\\u0041\\u0042\\u0043\\u0044", result);
    }
}
