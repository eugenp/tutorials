package com.baeldung.java14.textblocks;

public class TextBlocks14 {
    public String getIgnoredNewLines() {
        return """
            This is a long test which looks to \
            have a newline but actually does not""";
    }

    public String getEscapedSpaces() {
        return """
            line 1
            line 2       \s
            """;
    }
}
