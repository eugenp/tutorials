package com.baeldung.regexp;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;

public class EscapingCharsTest {
    @Test
    public void givenRegexWithDot_whenMatchingStr_thenMatches() {
        String strInput = "foof";
        String strRegex = "foo.";
        EscapingChars e = new EscapingChars();

        assertEquals(true, e.isMatching(strInput, strRegex));
    }

    @Test
    public void givenRegexWithDotEsc_whenMatchingStr_thenNotMatching() {
        String strInput = "foof";
        String strRegex = "foo\\.";
        EscapingChars e = new EscapingChars();

        assertEquals(false, e.isMatching(strInput, strRegex));
    }

    @Test
    public void givenRegexWithPipeEscaped_whenSplitStr_thenSplits() {
        String strInput = "foo|bar|hello|world";
        String strRegex = "\\Q|\\E";
        EscapingChars e = new EscapingChars();

        assertEquals(4, e.splitAndCountWords(strInput, strRegex));
    }

    @Test
    public void givenRegexWithPipeEscQuoteMeth_whenSplitStr_thenSplits() {
        String strInput = "foo|bar|hello|world";
        String strRegex = "|";
        EscapingChars e = new EscapingChars();

        assertEquals(4, e.splitAndCountWordsUsingQuoteMethod(strInput, strRegex));
    }

    @Test
    public void givenRegexWithDollar_whenReplacing_thenNotReplace() {
        String strInput = "I gave $50 to my brother.He bought candy for $35. Now he has $15 left.";
        String strRegex = "$";
        String strReplacement = "�";
        String output = "I gave �50 to my brother.He bought candy for �35. Now he has �15 left.";
        EscapingChars e = new EscapingChars();

        assertThat(output, not(equalTo(e.changeCurrencySymbol(strInput, strRegex, strReplacement))));
    }

    @Test
    public void givenRegexWithDollarEsc_whenReplacing_thenReplace() {
        String strInput = "I gave $50 to my brother. He bought candy for $35. Now he has $15 left.";
        String strRegex = "\\$";
        String strReplacement = "�";
        String output = "I gave �50 to my brother. He bought candy for �35. Now he has �15 left.";
        EscapingChars e = new EscapingChars();

        assertEquals(output, e.changeCurrencySymbol(strInput, strRegex, strReplacement));
    }
}
