package com.baeldung.javadiffutils;

import com.github.difflib.DiffUtils;
import com.github.difflib.patch.Patch;
import java.util.List;

public class TextComparatorUtil {
    public static Patch<String> compare(List<String> original, List<String> revised) {
        return DiffUtils.diff(original, revised);
    }
}
