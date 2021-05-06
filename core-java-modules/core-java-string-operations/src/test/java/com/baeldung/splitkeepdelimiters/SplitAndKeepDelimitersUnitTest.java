package com.baeldung.splitkeepdelimiters;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import com.google.common.base.Splitter;

public class SplitAndKeepDelimitersUnitTest {

    private final String positivelookAheadRegex = "((?=@))";
    private final String positivelookBehindRegex = "((?<=@))";
    private final String positivelookAroundRegex = "((?=@)|(?<=@))";
    private final String positiveLookAroundMultiDelimiterRegex = "((?<=;|:|,|#|@|~)|(?=;|:|,|#|@|~))";

    private String text = "Hello@World@This@Is@A@Java@Program";
    private String textMixed = "@Hello;World@This:Is,A#Java~Program";
    private String textMixed2 = "pg-no.10@hello;world@this:is,a#10words|Java~Program";

    @Test
    public void givenString_splitAndKeepDelimiters_using_javaLangString() {

        assertThat(text.split(positivelookAheadRegex)).containsExactly("Hello", "@World", "@This", "@Is", "@A", "@Java", "@Program");

        assertThat(text.split(positivelookBehindRegex)).containsExactly("Hello@", "World@", "This@", "Is@", "A@", "Java@", "Program");

        assertThat(text.split(positivelookAroundRegex)).containsExactly("Hello", "@", "World", "@", "This", "@", "Is", "@", "A", "@", "Java", "@", "Program");

        assertThat(textMixed.split(positiveLookAroundMultiDelimiterRegex)).containsExactly("@", "Hello", ";", "World", "@", "This", ":", "Is", ",", "A", "#", "Java", "~", "Program");

    }

    @Test
    public void givenString_splitAndKeepDelimiters_using_ApacheCommonsLang3StringUtils() {

        assertThat(StringUtils.splitByCharacterType(textMixed2)).containsExactly("pg", "-", "no", ".", "10", "@", "hello", ";", "world", "@", "this", ":", "is", ",", "a", "#", "10", "words", "|", "J", "ava", "~", "P", "rogram");

    }

    @Test
    public void givenString_splitAndKeepDelimiters_using_GuavaSplitter() {

        assertThat(Splitter.onPattern(positiveLookAroundMultiDelimiterRegex)
            .splitToList(textMixed)).containsExactly("@", "Hello", ";", "World", "@", "This", ":", "Is", ",", "A", "#", "Java", "~", "Program");

        assertThat(Splitter.on(Pattern.compile(positiveLookAroundMultiDelimiterRegex))
            .splitToList(textMixed)).containsExactly("@", "Hello", ";", "World", "@", "This", ":", "Is", ",", "A", "#", "Java", "~", "Program");

    }
}
