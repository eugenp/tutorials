package com.baeldung.streams.regexmatches;

import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class StreamFromRegexUnitTest {

    @Test
    public void whenInputStringIncludeLettersAndNumbers_ThenReturnStreamOfNumbers() {
        List<String> result = StreamFromRegexUtil.getStream("There are 3 apples and 2 bananas on the table.", "\\d+")
                .collect(Collectors.toList());
        assertEquals(asList("3", "2"), result);
    }

}
