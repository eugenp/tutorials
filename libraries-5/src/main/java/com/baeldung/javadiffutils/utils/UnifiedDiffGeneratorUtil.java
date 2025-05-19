package com.baeldung.javadiffutils.Utils;

import com.github.difflib.DiffUtils;
import com.github.difflib.UnifiedDiffUtils;

import java.util.List;

public class UnifiedDiffGeneratorUtil {
    public List<String> generate(List<String> original, List<String> revised, String fileName) {
        var patch = DiffUtils.diff(original, revised);
        return UnifiedDiffUtils.generateUnifiedDiff(fileName, fileName + ".new", original, patch, 3);
    }
}
