package com.baeldung.regex.squarebrackets;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

import com.google.common.collect.Lists;

public class ExtractTextBetweenSquareBracketsUnitTest {
    static final String INPUT1 = "some text [THE IMPORTANT MESSAGE] something else";
    static final String EXPECTED1 = "THE IMPORTANT MESSAGE";

    static final String INPUT2 = "[La La Land], [The last Emperor], and [Life of Pi] are all great movies.";
    static final List<String> EXPECTED2 = Lists.newArrayList("La La Land", "The last Emperor", "Life of Pi");

    @Test
    void whenUsingDotStarOnInput1_thenGetExpectedResult() {
        String result = null;
        String rePattern = "\\[(.*)]";
        Pattern p = Pattern.compile(rePattern);
        Matcher m = p.matcher(INPUT1);
        if (m.find()) {
            result = m.group(1);
        }
        assertThat(result).isEqualTo(EXPECTED1);
    }

    @Test
    void whenUsingCharClassOnInput1_thenGetExpectedResult() {
        String result = null;
        String rePattern = "\\[([^]]*)";
        Pattern p = Pattern.compile(rePattern);
        Matcher m = p.matcher(INPUT1);
        if (m.find()) {
            result = m.group(1);
        }
        assertThat(result).isEqualTo(EXPECTED1);
    }

    @Test
    void whenUsingSplitOnInput1_thenGetExpectedResult() {
        String[] strArray = INPUT1.split("[\\[\\]]", -1);
        String result = strArray.length == 3 ? strArray[1] : null;

        assertThat(result).isEqualTo(EXPECTED1);
    }

    @Test
    void whenUsingSplitWithLimit_thenGetExpectedResult() {
        String[] strArray = "[THE IMPORTANT MESSAGE]".split("[\\[\\]]");
        assertThat(strArray).hasSize(2)
          .containsExactly("", "THE IMPORTANT MESSAGE");

        strArray = "[THE IMPORTANT MESSAGE]".split("[\\[\\]]", -1);
        assertThat(strArray).hasSize(3)
          .containsExactly("", "THE IMPORTANT MESSAGE", "");
    }

    @Test
    void whenUsingNonGreedyOnInput2_thenGetExpectedResult() {
        List<String> result = new ArrayList<>();
        String rePattern = "\\[(.*?)]";
        Pattern p = Pattern.compile(rePattern);
        Matcher m = p.matcher(INPUT2);
        while (m.find()) {
            result.add(m.group(1));
        }
        assertThat(result).isEqualTo(EXPECTED2);
    }

    @Test
    void whenUsingCharClassOnInput2_thenGetExpectedResult() {
        List<String> result = new ArrayList<>();
        String rePattern = "\\[([^]]*)";
        Pattern p = Pattern.compile(rePattern);
        Matcher m = p.matcher(INPUT2);
        while (m.find()) {
            result.add(m.group(1));
        }
        assertThat(result).isEqualTo(EXPECTED2);
    }

    @Test
    void whenUsingSplitInput2_thenGetExpectedResult() {
        List<String> result = new ArrayList<>();
        String[] strArray = INPUT2.split("[\\[\\]]", -1);
        for (int i = 1; i < strArray.length; i += 2) {
            result.add(strArray[i]);
        }
        assertThat(result).isEqualTo(EXPECTED2);
    }

}