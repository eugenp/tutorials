package com.baeldung.regexp;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

public class EscapingCharsUnitTest {
    @Test
    public void givenRegexWithDot_whenMatchingStr_thenMatches() {
        String strInput = "foof";
        String strRegex = "foo.";

        assertEquals(true, strInput.matches(strRegex));
    }

    @Test
    public void givenRegexWithDotEsc_whenMatchingStr_thenNotMatching() {
        String strInput = "foof";
        String strRegex = "foo\\.";

        assertEquals(false, strInput.matches(strRegex));
    }

    @Test
    public void givenRegexWithPipeEscaped_whenSplitStr_thenSplits() {
        String strInput = "foo|bar|hello|world";
        String strRegex = "\\Q|\\E";

        assertEquals(4, strInput.split(strRegex).length);
    }

    @Test
    public void givenRegexWithPipeEscQuoteMeth_whenSplitStr_thenSplits() {
        String strInput = "foo|bar|hello|world";
        String strRegex = "|";

        assertEquals(4, strInput.split(Pattern.quote(strRegex)).length);
    }

    @Test
    public void givenRegexWithDollar_whenReplacing_thenNotReplace() {
        String strInput = "I gave $50 to my brother."
          + "He bought candy for $35. Now he has $15 left.";
        String strRegex = "$";
        String strReplacement = "£";
        String output = "I gave £50 to my brother."
          + "He bought candy for £35. Now he has £15 left.";
        Pattern p = Pattern.compile(strRegex);
        Matcher m = p.matcher(strInput);

        assertThat(output, not(equalTo(m.replaceAll(strReplacement))));
    }

    @Test
    public void givenRegexWithDollarEsc_whenReplacing_thenReplace() {
        String strInput = "I gave $50 to my brother."
          + "He bought candy for $35. Now he has $15 left.";
        String strRegex = "\\$";
        String strReplacement = "£";
        String output = "I gave £50 to my brother."
          + "He bought candy for £35. Now he has £15 left.";
        Pattern p = Pattern.compile(strRegex);
        Matcher m = p.matcher(strInput);

        assertEquals(output, m.replaceAll(strReplacement));
    }
}
