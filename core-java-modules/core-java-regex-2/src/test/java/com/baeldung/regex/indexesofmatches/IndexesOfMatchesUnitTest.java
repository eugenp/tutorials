package com.baeldung.regex.indexesofmatches;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

public class IndexesOfMatchesUnitTest {
    private static final String INPUT = "This line contains <the first value>, <the second value>, and <the third value>.";

    @Test
    void whenUsingNorCharClass_thenGetExpectedTexts() {
        Pattern pattern = Pattern.compile("<[^>]*>");
        Matcher matcher = pattern.matcher(INPUT);
        List<String> result = new ArrayList<>();
        while (matcher.find()) {
            result.add(matcher.group());
        }
        assertThat(result).containsExactly("<the first value>", "<the second value>", "<the third value>");
    }

    @Test
    void whenCallingMatcherEnd_thenGetIndexesAfterTheMatchSequence() {
        Pattern pattern = Pattern.compile("456");
        Matcher matcher = pattern.matcher("0123456789");
        String result = null;
        int startIdx = -1;
        int endIdx = -1;
        if (matcher.find()) {
            result = matcher.group();
            startIdx = matcher.start();
            endIdx = matcher.end();
        }
        assertThat(result).isEqualTo("456");
        assertThat(startIdx).isEqualTo(4);
        assertThat(endIdx).isEqualTo(7);
    }

    @Test
    void whenUsingMatcherStartAndEnd_thenGetIndexesOfMatches() {
        Pattern pattern = Pattern.compile("<[^>]*>");
        Matcher matcher = pattern.matcher(INPUT);
        List<String> result = new ArrayList<>();
        Map<Integer, Integer> indexesOfMatches = new LinkedHashMap<>();
        while (matcher.find()) {
            result.add(matcher.group());
            indexesOfMatches.put(matcher.start(), matcher.end());
        }
        assertThat(result).containsExactly("<the first value>", "<the second value>", "<the third value>");
        assertThat(indexesOfMatches.entrySet()).map(entry -> INPUT.substring(entry.getKey(), entry.getValue()))
          .containsExactly("<the first value>", "<the second value>", "<the third value>");
    }

    @Test
    void whenUsingMatcherStartAndEndWithGroupIdx_thenGetIndexesOfMatches() {
        Pattern pattern = Pattern.compile("<([^>]*)>");
        Matcher matcher = pattern.matcher(INPUT);
        List<String> result = new ArrayList<>();
        Map<Integer, Integer> indexesOfMatches = new LinkedHashMap<>();
        while (matcher.find()) {
            result.add(matcher.group(1));
            indexesOfMatches.put(matcher.start(1), matcher.end(1));
        }
        assertThat(result).containsExactly("the first value", "the second value", "the third value");

        assertThat(indexesOfMatches.entrySet()).map(entry -> INPUT.substring(entry.getKey(), entry.getValue()))
          .containsExactly("the first value", "the second value", "the third value");
    }
}