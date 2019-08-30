package com.baeldung.stringdiff;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.apache.commons.lang3.StringUtils;
import org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch;
import org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch.Operation;
import org.junit.Test;

public class StringDiffUnitTest {

    private DiffMatchPatch diffMatchPatch = new DiffMatchPatch();

    // Test samples
    private final String text1 = "ABCDELMN";
    private final String text2 = "ABCFGLMN";

    @Test
    public void givenTwoStrings_whenDiffMatchPatch_thenReturnCorrectDiff() {
        assertThat(diffMatchPatch.diffMain(text1, text2, false), containsInAnyOrder(
            new DiffMatchPatch.Diff(Operation.EQUAL, "ABC"),
            new DiffMatchPatch.Diff(Operation.DELETE, "DE"),
            new DiffMatchPatch.Diff(Operation.INSERT, "FG"),
            new DiffMatchPatch.Diff(Operation.EQUAL, "LMN")));
        assertThat(diffMatchPatch.diffMain(text2, text1, false), containsInAnyOrder(
            new DiffMatchPatch.Diff(Operation.EQUAL, "ABC"),
            new DiffMatchPatch.Diff(Operation.INSERT, "DE"),
            new DiffMatchPatch.Diff(Operation.DELETE, "FG"),
            new DiffMatchPatch.Diff(Operation.EQUAL, "LMN")));
    }

    @Test
    public void givenTwoStrings_whenStringUtilsDifference_thenReturnCorrectDiff() {
        assertThat(StringUtils.difference(text1, text2), is("FGLMN"));
        assertThat(StringUtils.difference(text2, text1), is("DELMN"));
    }
}
