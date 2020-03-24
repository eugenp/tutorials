package com.baeldung.text;

import org.apache.commons.text.similarity.LongestCommonSubsequence;
import org.apache.commons.text.similarity.LongestCommonSubsequenceDistance;
import org.junit.Assert;
import org.junit.Test;

public class LongestCommonSubsequenceUnitTest {

    @Test
    public void whenCompare_thenCorrect() {
        LongestCommonSubsequence lcs = new LongestCommonSubsequence();
        int countLcs = lcs.apply("New York", "New Hampshire");

        Assert.assertEquals(5, countLcs);
    }

    @Test
    public void whenCalculateDistance_thenCorrect() {
        LongestCommonSubsequenceDistance lcsd = new LongestCommonSubsequenceDistance();
        int countLcsd = lcsd.apply("New York", "New Hampshire");

        Assert.assertEquals(11, countLcsd);
    }
}
