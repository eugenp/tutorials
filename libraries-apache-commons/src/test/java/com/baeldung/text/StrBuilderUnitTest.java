package com.baeldung.text;

import org.apache.commons.text.StrBuilder;
import org.junit.Assert;
import org.junit.Test;

public class StrBuilderUnitTest {

    @Test
    public void whenReplaced_thenCorrect() {
        StrBuilder strBuilder = new StrBuilder("example StrBuilder!");
        strBuilder.replaceAll("example", "new");

        Assert.assertEquals(new StrBuilder("new StrBuilder!"), strBuilder);
    }

    @Test
    public void whenCleared_thenEmpty() {
        StrBuilder strBuilder = new StrBuilder("example StrBuilder!");
        strBuilder.clear();

        Assert.assertEquals(new StrBuilder(""), strBuilder);
    }
}