package com.baeldung.datetime;

import org.junit.Assert;
import org.junit.Test;

public class UseSimpleDateFormatTest {
    private UseSimpleDateFormat useSimpleDateFormat = new UseSimpleDateFormat();

    @Test
    public void givenValues_whenUsingFactoryOf_thenLocalTime() {
        Assert.assertEquals("2024-09-04 13:42:22 +0530", useSimpleDateFormat.useFormat());
    }
}
