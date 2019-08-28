package com.baeldung.stringdiff;

import org.apache.commons.lang3.StringUtils;
import org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch;
import org.junit.Test;

public class StringDiffUnitTest {

    private DiffMatchPatch diffMatchPatch = new DiffMatchPatch();

    @Test
    public void testStringDiff() {
        String text1 = "ABCDELMN";
        String text2 = "ABCFGLMN";

        // Diff with Diff-Match-Patch
        System.out.println(diffMatchPatch.diffMain(text1, text2, false));
        System.out.println(diffMatchPatch.diffMain(text2, text1, false));

        // Diff with StringUtils
        System.out.println(StringUtils.difference(text1, text2));
        System.out.println(StringUtils.difference(text2, text1));
    }
}
