package com.baeldung.javadiffutils;

import org.junit.Test;

import java.util.List;

public class SideBySideViewUtilTest {

    @Test
    public void givenDifferentLists_whenDisplayCalled_thenNoExceptionThrown() {
        List<String> original = List.of("line1", "line2", "line3");
        List<String> revised = List.of("line1", "line2-modified", "line3", "line4");

        SideBySideViewUtil.display(original, revised);
    }

}
