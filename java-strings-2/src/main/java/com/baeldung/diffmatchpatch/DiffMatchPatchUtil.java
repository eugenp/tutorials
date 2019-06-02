package com.baeldung.diffmatchpatch;

import org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;


public class DiffMatchPatchUtil {
    private static DiffMatchPatch dmp = new DiffMatchPatch();

    public static LinkedList<DiffMatchPatch.Diff> compareStringsHumanReadable(String string1, String string2) {
        LinkedList<DiffMatchPatch.Diff> diffs = dmp.diffMain(string1, string2);
        dmp.diffCleanupSemantic(diffs);
        return diffs;
    }

    public static HashMap<String, LinkedList<DiffMatchPatch.Diff>> compareFiles(String fileName1, String fileName2) throws
        IOException {
        HashMap<String, LinkedList<DiffMatchPatch.Diff>> lineByLineDiffs = new HashMap<>();

        BufferedReader bufferedReader1 = new BufferedReader(new FileReader(fileName1));
        BufferedReader bufferedReader2 = new BufferedReader(new FileReader(fileName2));

        String file1Line;
        String file2Line;
        int lineCounter = 1;

        while ((file1Line = bufferedReader1.readLine()) != null) {
            if ((file2Line = bufferedReader2.readLine()) != null) {
                lineByLineDiffs.put("Line " + lineCounter, compareStringsHumanReadable(file1Line, file2Line));
                lineCounter++;
            } else {
                lineByLineDiffs.put("Line " + lineCounter, compareStringsHumanReadable(file1Line, ""));
                lineCounter++;
            }
        }

        while ((file2Line = bufferedReader2.readLine()) != null) {
            lineByLineDiffs.put("Line " + lineCounter, compareStringsHumanReadable("", file2Line));
            lineCounter++;
        }

        return lineByLineDiffs;
    }
}
