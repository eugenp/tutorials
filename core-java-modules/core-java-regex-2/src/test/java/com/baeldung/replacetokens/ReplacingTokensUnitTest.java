package com.baeldung.replacetokens;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.baeldung.replacetokens.ReplacingTokens.TITLE_CASE_PATTERN;
import static com.baeldung.replacetokens.ReplacingTokens.replaceTokens;
import static org.assertj.core.api.Assertions.assertThat;

public class ReplacingTokensUnitTest {
    private static final String EXAMPLE_INPUT = "First 3 Capital Words! then 10 TLAs, I Found";
    private static final String EXAMPLE_INPUT_PROCESSED = "first 3 capital words! then 10 TLAs, i found";

    @Test
    public void whenFindMatches_thenTitleWordsFound() {
        Matcher matcher = TITLE_CASE_PATTERN.matcher(EXAMPLE_INPUT);
        List<String> matches = new ArrayList<>();
        while (matcher.find()) {
            matches.add(matcher.group(1));
        }

        assertThat(matches)
          .containsExactly("First", "Capital", "Words", "I", "Found");
    }

    @Test
    public void exploreMatches() {
        Matcher matcher = TITLE_CASE_PATTERN.matcher(EXAMPLE_INPUT);
        while (matcher.find()) {
            System.out.println("Match: " + matcher.group(0));
            System.out.println("Start: " + matcher.start());
            System.out.println("End: " + matcher.end());
        }
    }

    @Test
    public void whenReplaceTokensWithLowerCase() {
        assertThat(ReplacingTokens.replaceTitleCaseWithLowerCase(EXAMPLE_INPUT))
          .isEqualTo(EXAMPLE_INPUT_PROCESSED);
    }

    @Test
    public void whenReplaceTokensWithLowerCaseUsingGeneralPurpose() {
        assertThat(replaceTokens("First 3 Capital Words! then 10 TLAs, I Found",
          TITLE_CASE_PATTERN,
          match -> match.group(1).toLowerCase()))
          .isEqualTo("first 3 capital words! then 10 TLAs, i found");
    }

    @Test
    public void escapeRegexCharacters() {
        Pattern regexCharacters = Pattern.compile("[<(\\[{\\\\^\\-=$!|\\]})?*+.>]");

        assertThat(replaceTokens("A regex character like [",
          regexCharacters,
          match -> "\\" + match.group()))
          .isEqualTo("A regex character like \\[");
    }

    @Test
    public void replacePlaceholders() {
        Pattern placeholderPattern = Pattern.compile("\\$\\{(?<placeholder>[A-Za-z0-9-_]+)}");

        Map<String, String> placeholderValues = new HashMap<>();
        placeholderValues.put("name", "Bill");
        placeholderValues.put("company", "Baeldung");

        assertThat(replaceTokens("Hi ${name} at ${company}",
          placeholderPattern,
          match -> placeholderValues.get(match.group("placeholder"))))
          .isEqualTo("Hi Bill at Baeldung");
    }
}
