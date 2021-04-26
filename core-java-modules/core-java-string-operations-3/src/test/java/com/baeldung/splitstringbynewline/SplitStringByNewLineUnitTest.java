package com.baeldung.splitstringbynewline;

import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SplitStringByNewLineUnitTest {
    @Test
    public void givenString_whenSplitByNewLineUsingSystemLineSeparator_thenReturnsArray() {
        assertThat("Line1\nLine2\nLine3".split(System.lineSeparator())).containsExactly("Line1", "Line2", "Line3");
    }

    @Test
    public void givenString_whenSplitByNewLineUsingRegularExpressionPattern_thenReturnsArray() {
        assertThat("Line1\nLine2\nLine3".split("\\r?\\n|\\r")).containsExactly("Line1", "Line2", "Line3");

        assertThat("Line1\rLine2\rLine3".split("\\r?\\n|\\r")).containsExactly("Line1", "Line2", "Line3");

        assertThat("Line1\r\nLine2\r\nLine3".split("\\r?\\n|\\r")).containsExactly("Line1", "Line2", "Line3");
    }

    @Test
    public void givenString_whenSplitByNewLineUsingJava8Pattern_thenReturnsArray() {
        assertThat("Line1\nLine2\nLine3".split("\\R")).containsExactly("Line1", "Line2", "Line3");

        assertThat("Line1\rLine2\rLine3".split("\\R")).containsExactly("Line1", "Line2", "Line3");

        assertThat("Line1\r\nLine2\r\nLine3".split("\\R")).containsExactly("Line1", "Line2", "Line3");
    }
}
