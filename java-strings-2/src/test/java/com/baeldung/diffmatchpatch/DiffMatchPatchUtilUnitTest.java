package com.baeldung.diffmatchpatch;

import org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

import static org.junit.Assert.assertEquals;

public class DiffMatchPatchUtilUnitTest {
    private String string1 = "Go and fetch some water";
    private String string2 = "Go and grab some soda";

    private DiffMatchPatch dmp = new DiffMatchPatch();

    @Test
    public void compareTwoDifferentStrings() {
        DiffMatchPatch.Diff diff1 = new DiffMatchPatch.Diff(DiffMatchPatch.Operation.EQUAL, "Go and ");
        DiffMatchPatch.Diff diff2 = new DiffMatchPatch.Diff(DiffMatchPatch.Operation.DELETE, "fetch");
        DiffMatchPatch.Diff diff3 = new DiffMatchPatch.Diff(DiffMatchPatch.Operation.INSERT, "grab");
        DiffMatchPatch.Diff diff4 = new DiffMatchPatch.Diff(DiffMatchPatch.Operation.EQUAL, " some ");
        DiffMatchPatch.Diff diff5 = new DiffMatchPatch.Diff(DiffMatchPatch.Operation.DELETE, "w");
        DiffMatchPatch.Diff diff6 = new DiffMatchPatch.Diff(DiffMatchPatch.Operation.INSERT, "sod");
        DiffMatchPatch.Diff diff7 = new DiffMatchPatch.Diff(DiffMatchPatch.Operation.EQUAL, "a");
        DiffMatchPatch.Diff diff8 = new DiffMatchPatch.Diff(DiffMatchPatch.Operation.DELETE, "ter");

        LinkedList<DiffMatchPatch.Diff> expectedDiffs = new LinkedList<>();
        expectedDiffs.add(diff1);
        expectedDiffs.add(diff2);
        expectedDiffs.add(diff3);
        expectedDiffs.add(diff4);
        expectedDiffs.add(diff5);
        expectedDiffs.add(diff6);
        expectedDiffs.add(diff7);
        expectedDiffs.add(diff8);

        LinkedList<DiffMatchPatch.Diff> diffs = dmp.diffMain(string1, string2);

        assertEquals(expectedDiffs, diffs);
    }

    @Test
    public void compareTwoDifferentStringsHumanReadable() {
        DiffMatchPatch.Diff diff1 = new DiffMatchPatch.Diff(DiffMatchPatch.Operation.EQUAL, "Go and ");
        DiffMatchPatch.Diff diff2 = new DiffMatchPatch.Diff(DiffMatchPatch.Operation.DELETE, "fetch");
        DiffMatchPatch.Diff diff3 = new DiffMatchPatch.Diff(DiffMatchPatch.Operation.INSERT, "grab");
        DiffMatchPatch.Diff diff4 = new DiffMatchPatch.Diff(DiffMatchPatch.Operation.EQUAL, " some ");
        DiffMatchPatch.Diff diff5 = new DiffMatchPatch.Diff(DiffMatchPatch.Operation.DELETE, "water");
        DiffMatchPatch.Diff diff6 = new DiffMatchPatch.Diff(DiffMatchPatch.Operation.INSERT, "soda");

        LinkedList<DiffMatchPatch.Diff> expectedDiffs = new LinkedList<>();
        expectedDiffs.add(diff1);
        expectedDiffs.add(diff2);
        expectedDiffs.add(diff3);
        expectedDiffs.add(diff4);
        expectedDiffs.add(diff5);
        expectedDiffs.add(diff6);

        LinkedList<DiffMatchPatch.Diff> diffs = DiffMatchPatchUtil.compareStringsHumanReadable(string1, string2);

        assertEquals(expectedDiffs, diffs);
    }

    @Test
    public void compareTwoFiles() throws IOException {
        DiffMatchPatch.Diff line1Diff1 = new DiffMatchPatch.Diff(DiffMatchPatch.Operation.EQUAL, "Hello World!");

        LinkedList<DiffMatchPatch.Diff> line1Diffs = new LinkedList<>();
        line1Diffs.add(line1Diff1);

        DiffMatchPatch.Diff line2Diff1 = new DiffMatchPatch.Diff(DiffMatchPatch.Operation.DELETE, "How");
        DiffMatchPatch.Diff line2Diff2 = new DiffMatchPatch.Diff(DiffMatchPatch.Operation.INSERT, "What");
        DiffMatchPatch.Diff line2Diff3 = new DiffMatchPatch.Diff(DiffMatchPatch.Operation.EQUAL, " are you");
        DiffMatchPatch.Diff line2Diff4 = new DiffMatchPatch.Diff(DiffMatchPatch.Operation.INSERT, " up to");
        DiffMatchPatch.Diff line2Diff5 = new DiffMatchPatch.Diff(DiffMatchPatch.Operation.EQUAL, "?");

        LinkedList<DiffMatchPatch.Diff> line2Diffs = new LinkedList<>();
        line2Diffs.add(line2Diff1);
        line2Diffs.add(line2Diff2);
        line2Diffs.add(line2Diff3);
        line2Diffs.add(line2Diff4);
        line2Diffs.add(line2Diff5);

        DiffMatchPatch.Diff line3Diff1 = new DiffMatchPatch.Diff(DiffMatchPatch.Operation.EQUAL, "I'm ");
        DiffMatchPatch.Diff line3Diff2 = new DiffMatchPatch.Diff(DiffMatchPatch.Operation.DELETE, "great");
        DiffMatchPatch.Diff line3Diff3 = new DiffMatchPatch.Diff(DiffMatchPatch.Operation.INSERT, "well");
        DiffMatchPatch.Diff line3Diff4 = new DiffMatchPatch.Diff(DiffMatchPatch.Operation.EQUAL, ", and you?");

        LinkedList<DiffMatchPatch.Diff> line3Diffs = new LinkedList<>();
        line3Diffs.add(line3Diff1);
        line3Diffs.add(line3Diff2);
        line3Diffs.add(line3Diff3);
        line3Diffs.add(line3Diff4);

        DiffMatchPatch.Diff line4Diff1 = new DiffMatchPatch.Diff(DiffMatchPatch.Operation.DELETE, "Wonderful to hear!");

        LinkedList<DiffMatchPatch.Diff> line4Diffs = new LinkedList<>();
        line4Diffs.add(line4Diff1);

        HashMap<String, LinkedList<DiffMatchPatch.Diff>> expectedResults = new HashMap<>();
        expectedResults.put("Line 1", line1Diffs);
        expectedResults.put("Line 2", line2Diffs);
        expectedResults.put("Line 3", line3Diffs);
        expectedResults.put("Line 4", line4Diffs);

        HashMap<String, LinkedList<DiffMatchPatch.Diff>> actualResults = DiffMatchPatchUtil.compareFiles("src/test/resources/file1.txt", "src/test/resources/file2.txt");

        assertEquals(expectedResults, actualResults);
    }
}
