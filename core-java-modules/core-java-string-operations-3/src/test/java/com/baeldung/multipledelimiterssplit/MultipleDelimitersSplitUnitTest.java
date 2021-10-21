package com.baeldung.multipledelimiterssplit;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterators;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.regex.Pattern;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MultipleDelimitersSplitUnitTest {

    @Test
    public void givenString_whenSplittingByMultipleDelimitersWithRegEx_thenStringSplit(){
        String example = "Mary;Thomas:Jane-Kate";
        String[] names = example.split(";|:|-");
        Assertions.assertEquals(4, names.length);
    }

    @Test
    public void givenString_whenSplittingByWithCharMatcherAndOnMethod_thenStringSplit(){
        String example = "Mary;Thomas:Jane-Kate";
        Iterable<String> names = Splitter.on(CharMatcher.anyOf(";:-")).split(example);
        Assertions.assertEquals(4, Iterators.size(names.iterator()));
    }

    @Test
    public void givenString_whenSplittingByWithRegexAndOnPatternMethod_thenStringSplit(){
        String example = "Mary;Thomas:Jane-Kate";
        Iterable<String> names = Splitter.on(Pattern.compile(";|:|-")).split(example);
        Assertions.assertEquals(4, Iterators.size(names.iterator()));
    }

    @Test
    public void givenString_whenSplittingByMultipleDelimitersWithGuava_thenStringSplit(){
        String example = "Mary;Thomas:Jane-Kate";
        Iterable<String> names = Splitter.onPattern(";|:|-").split(example);
        Assertions.assertEquals(4, Iterators.size(names.iterator()));
    }

    @Test
    public void givenString_whenSplittingByMultipleDelimitersWithApache_thenStringSplit(){
        String example = "Mary;Thomas:Jane-Kate";
        String[] names = StringUtils.split(example, ";:-");
        Assertions.assertEquals(4, names.length);
    }

}

