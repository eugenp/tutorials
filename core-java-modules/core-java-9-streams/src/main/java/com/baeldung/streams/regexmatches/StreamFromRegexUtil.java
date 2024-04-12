package com.baeldung.streams.regexmatches;

import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class StreamFromRegexUtil {

    public static Stream<String> getStream(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.results().map(MatchResult::group);
    }

}
