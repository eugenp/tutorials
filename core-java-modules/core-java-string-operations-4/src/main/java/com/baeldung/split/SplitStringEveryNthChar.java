package com.baeldung.split;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;

public class SplitStringEveryNthChar {

    public static List<String> usingSplitMethod(String text, int n) {
        String[] results = text.split("(?<=\\G.{" + n + "})");

        return Arrays.asList(results);
    }

    public static List<String> usingSubstringMethod(String text, int n) {
        List<String> results = new ArrayList<>();
        int length = text.length();

        for (int i = 0; i < length; i += n) {
            results.add(text.substring(i, Math.min(length, i + n)));
        }

        return results;
    }

    public static List<String> usingPattern(String text, int n) {
        List<String> results = new ArrayList<>();

        Pattern pattern = Pattern.compile(".{1," + n + "}");
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            String match = text.substring(matcher.start(), matcher.end());
            results.add(match);
        }

        return results;
    }

    public static List<String> usingGuava(String text, int n) {
        Iterable<String> parts = Splitter.fixedLength(n)
            .split(text);

        return ImmutableList.copyOf(parts);
    }

}
