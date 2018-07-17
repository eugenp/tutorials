package com.baeldung.text;

import org.apache.commons.text.diff.EditScript;
import org.apache.commons.text.diff.StringsComparator;
import org.junit.Assert;
import org.junit.Test;

public class DiffUnitTest {

    @Test
    public void whenEditScript_thenCorrect() {
        StringsComparator cmp = new StringsComparator("ABCFGH", "BCDEFG");
        EditScript<Character> script = cmp.getScript();
        int mod = script.getModifications();

        Assert.assertEquals(4, mod);
    }
}
