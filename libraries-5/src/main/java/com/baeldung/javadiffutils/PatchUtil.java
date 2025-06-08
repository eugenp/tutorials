package com.baeldung.javadiffutils;

import com.github.difflib.DiffUtils;
import com.github.difflib.patch.PatchFailedException;

import java.util.List;

public class PatchUtil {
    public static List<String> apply(List<String> original, List<String> revised) throws PatchFailedException {
        var patch = DiffUtils.diff(original, revised);
        return DiffUtils.patch(original, patch);
    }
}
